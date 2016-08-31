package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrganizationPact {
    private int id;
    private String pactName;
    private double sum;
    private String dateSumUpdate;

    @Autowired
    private DbJdbcModel dbMvc;

    public OrganizationPact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPactName() {
        return pactName;
    }

    public void setPactName(String pactName) {
        this.pactName = pactName;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDateSumUpdate() {
        return dateSumUpdate;
    }

    public void setDateSumUpdate(String dateSumUpdate) {
        this.dateSumUpdate = dateSumUpdate;
    }

    public ArrayList<OrganizationPact> getOrganizationPactList() {
        ArrayList<OrganizationPact> organizationPactArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from company_dogovor order by dog_Name";
        organizationPactArrayList = getOrganizationPactFromDbSelect(sql, null);
        return organizationPactArrayList;
    }

    public ArrayList<OrganizationPact> getOrganizationPactList(int idOrganization) {
        ArrayList<OrganizationPact> organizationPactArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "SELECT \n" +
                "company_dogovor.* \n" +
                ", s2.summa\n" +
                ", s2.DateValue\n" +
                "FROM company_dogovor\n" +
                "left join \n" +
                "(\n" +
                "select * from \n" +
                "(\n" +
                "SELECT \n" +
                "id,\n" +
                "id_organization,\n" +
                "id_dogovor,\n" +
                "summa,\n" +
                "DateValue\n" +
                "FROM organization_valuation\n" +
                "order by DateValue  desc\n" +
                ") s1\n" +
                "group by id_organization,id_dogovor\n" +
                ") s2 on s2.id_dogovor = company_dogovor.dog_id\n" +
                "where dog_Organization_id=? order by dog_Name";
        args.add(idOrganization);
        organizationPactArrayList = getOrganizationPactFromDbSelect(sql, args);
        return organizationPactArrayList;
    }

    public ArrayList<OrganizationPact> getOrganizationPactList(String idOrganization) {
        int id = -1;
        try {
            id = Integer.parseInt(idOrganization);
        }catch (Exception e){
            return null;
        }
        return getOrganizationPactList(id);
    }

    public OrganizationPact getOrganizationPact(String idPact) {
        ArrayList<OrganizationPact> organizationPactArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "SELECT \n" +
                "company_dogovor.* \n" +
                ", s2.summa\n" +
                ", s2.DateValue\n" +
                "FROM company_dogovor\n" +
                "left join \n" +
                "(\n" +
                "select * from \n" +
                "(\n" +
                "SELECT \n" +
                "id,\n" +
                "id_organization,\n" +
                "id_dogovor,\n" +
                "summa,\n" +
                "DateValue\n" +
                "FROM organization_valuation\n" +
                "order by DateValue  desc\n" +
                "limit 2000\n" +
                ") s1\n" +
                "group by id_organization,id_dogovor\n" +
                ") s2 on s2.id_dogovor = company_dogovor.dog_id\n" +
                "where dog_id=?";
        args.add(idPact);
        organizationPactArrayList = getOrganizationPactFromDbSelect(sql, args);
        if (organizationPactArrayList == null || organizationPactArrayList.size() == 0) return null;
        return organizationPactArrayList.get(0);
    }

    private ArrayList<OrganizationPact> getOrganizationPactFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> organizationPactListFromDb = null;
        organizationPactListFromDb = dbMvc.getSelectResult(sql, args);
        if (organizationPactListFromDb == null) return null;
        ArrayList<OrganizationPact> organizationPactArrayList = null;
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (Map row : organizationPactListFromDb) {
            if (organizationPactArrayList == null) organizationPactArrayList = new ArrayList<OrganizationPact>();
            OrganizationPact organizationPact = new OrganizationPact();
            organizationPactArrayList.add(organizationPact);
            Thread thread = new Thread(new GetOrganizationPactThread(organizationPact, row));
            thread.start();
            threadArrayList.add(thread);
        }
        for (Thread thread : threadArrayList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return organizationPactArrayList;
    }

    public String getOrgDogOpenSumById(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String orgDogId = request.getParameter("orgDogId");
        if (orgDogId == null) return "Не найден номер договора";
        OrganizationPact organizationPact = getOrganizationPact(orgDogId);
        if (organizationPact == null) return "не найден договор";
        return "" + Math.round(organizationPact.getSum());
    }

    private class GetOrganizationPactThread implements Runnable {
        OrganizationPact organizationPact;
        Map row;

        public GetOrganizationPactThread(OrganizationPact organizationPact, Map row) {
            this.organizationPact = organizationPact;
            this.row = row;
        }

        @Override
        public void run() {
            setOrganizationFromRow();
        }

        public void setOrganizationFromRow() {
            organizationPact.setId((Integer) row.get("dog_id"));
            organizationPact.setPactName(row.get("dog_Name").toString());
            organizationPact.setSum(((Float) row.get("summa")).doubleValue());
            organizationPact.setDateSumUpdate(row.get("DateValue").toString());
        }
    }
}
