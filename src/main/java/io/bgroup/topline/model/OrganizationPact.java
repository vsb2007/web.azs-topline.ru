package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class OrganizationPact {
    private int id;
    private String pactName;
    private int codeA;
    private double sum;
    private String dateSumUpdate;
    private int actual;

    @Autowired
    private DbJdbcModel dbMvc;

    public OrganizationPact() {
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getCodeA() {
        return codeA;
    }

    public void setCodeA(int codeA) {
        this.codeA = codeA;
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
        String sql = "SELECT " +
                "company_dogovor.* " +
                ", s2.summa " +
                ", s2.DateValue " +
                ", s2.Actual " +
                "FROM company_dogovor " +
                "left join " +
                "(" +
                "select * from " +
                "(" +
                "SELECT " +
                "id " +
                ", id_organization " +
                ", id_dogovor " +
                ", summa " +
                ", DateValue " +
                ", Actual " +
                "FROM organization_valuation " +
                "order by DateValue  desc " +
                ") s1 " +
                "group by id_organization,id_dogovor " +
                ") s2 on s2.id_dogovor = company_dogovor.dog_id " +
                "where dog_Organization_id=? order by dog_Name";
        args.add(idOrganization);
        organizationPactArrayList = getOrganizationPactFromDbSelect(sql, args);
        return organizationPactArrayList;
    }

    public ArrayList<OrganizationPact> getOrganizationPactList(String idOrganization) {
        int id = -1;
        try {
            id = Integer.parseInt(idOrganization);
        } catch (Exception e) {
            return null;
        }
        return getOrganizationPactList(id);
    }

    public OrganizationPact getOrganizationPact(String idPact) {
        ArrayList<OrganizationPact> organizationPactArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "SELECT \n" +
                "company_dogovor.* " +
                ", s2.summa " +
                ", s2.DateValue " +
                ", s2.Actual " +
                "FROM company_dogovor " +
                "left join " +
                "( " +
                "select * from " +
                "( " +
                "SELECT " +
                "id " +
                ", id_organization " +
                ", id_dogovor " +
                ", summa " +
                ", DateValue " +
                ", Actual " +
                "FROM organization_valuation " +
                "order by DateValue  desc " +
                ") s1 " +
                "group by id_organization,id_dogovor " +
                ") s2 on s2.id_dogovor = company_dogovor.dog_id " +
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
        double sumFromAzs = 0;
        if (organizationPact.getCodeA() > 0) {
            if (organizationPact.getActual() == 0) {
                sumFromAzs = getSumFromAzs(organizationPact);
            }
        }
        //System.out.println(organizationPact.getSum());
        //System.out.println(sumFromAzs);
        return "" + Math.round(organizationPact.getSum() - sumFromAzs);
    }

    private double getSumFromAzs(OrganizationPact organizationPact) {
        if (organizationPact == null) return 0;
        String sql = null;
        ArrayList<Object> args = new ArrayList<Object>();
        if (organizationPact.getActual() == 1) {
            sql = "select \n" +
                    "synthetic_account.SyntheticAccountKey\n" +
                    ", synthetic_account.COD_A\n" +
                    ", month_balance.SyntheticAccountKey\n" +
                    ", month_balance.YearNumber\n" +
                    ", month_balance.MonthNumber\n" +
                    ", month_balance.Expenses\n" +
                    "from synthetic_account\n" +
                    "left join month_balance on month_balance.SyntheticAccountKey = synthetic_account.SyntheticAccountKey \n" +
                    "and YearNumber = Year(now()) and MonthNumber = Month(now())\n" +
                    "where COD_A = ?";
            args.add(organizationPact.getCodeA());
        } else {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            sql = "select \n" +
                    "synthetic_account.SyntheticAccountKey\n" +
                    ", synthetic_account.COD_A\n" +
                    ", month_balance.SyntheticAccountKey\n" +
                    ", sum(month_balance.Expenses) as Expenses\n" +
                    "from synthetic_account\n" +
                    "left join month_balance on month_balance.SyntheticAccountKey = synthetic_account.SyntheticAccountKey \n";
            if (month == 1) {
                sql += "and ((YearNumber = ? and MonthNumber = 1)  or (YearNumber = ? and MonthNumber = 12))" +
                        "where COD_A = ?\n" +
                        "group by synthetic_account.COD_A";
                args.add(year);
                args.add(year-1);
                args.add(organizationPact.getCodeA());
            } else {
                sql += "\tand YearNumber = ? and (MonthNumber = ? or MonthNumber=?)\n" +
                        "where COD_A = ?\n" +
                        "group by synthetic_account.COD_A";
                args.add(year);
                args.add(month);
                args.add(month - 1);
                args.add(organizationPact.getCodeA());
            }
        }
        if (sql == null) return 0;
        List<Map<String, Object>> organizationPactListSumFromDb = null;
        organizationPactListSumFromDb = dbMvc.getSelectResultSncpc(sql, args);
        if (organizationPactListSumFromDb == null || organizationPactListSumFromDb.size() > 1)
            return 0;
        Map row = organizationPactListSumFromDb.get(0);
        Object expenses = row.get("Expenses");
        if (expenses == null) return 0;
        return (Double) expenses;
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
            Object dog_id = row.get("dog_id");
            if (dog_id != null)
                organizationPact.setId((Integer) dog_id);
            else return;
            Object dogName = row.get("dog_Name");
            if (dogName != null)
                organizationPact.setPactName(dogName.toString());
            else organizationPact.setPactName(null);
            Object summa = row.get("summa");
            if (summa != null)
                organizationPact.setSum(((Float) summa).doubleValue());
            else organizationPact.setSum(-1);
            Object date = row.get("DateValue");
            if (date != null)
                organizationPact.setDateSumUpdate(date.toString());
            else organizationPact.setDateSumUpdate(null);
            Object actual = row.get("Actual");
            if (actual != null)
                organizationPact.setActual((Integer) actual);
            else organizationPact.setActual(0);
            Object codeA = row.get("dog_KodOC");
            if (codeA != null)
                organizationPact.setCodeA((Integer) codeA);
            else organizationPact.setCodeA(-1);
        }
    }
}
