package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Organization {
    private int idOrganization;
    private String organizationName;
    private ArrayList<OrganizationPact> organizationPacts;

    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    private OrganizationPact organizationPactMvc;

    public Organization() {
    }

    public ArrayList<OrganizationPact> getOrganizationPacts() {
        return organizationPacts;
    }

    public void setOrganizationPacts(ArrayList<OrganizationPact> organizationPacts) {
        this.organizationPacts = organizationPacts;
    }

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public ArrayList<Organization> getOrganizationList() {
        ArrayList<Organization> organizationArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from organization order by Org_Name";
        organizationArrayList = getOrganizationFromDbSelect(sql, null);
        return organizationArrayList;
    }

    public ArrayList<Organization> getOrganizationListWithSaleBids() {
        ArrayList<Organization> organizationArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from organization " +
                "where Org_id in (select sale_id_org from salebid where sale_is_close = 0 and sale_is_done = 0 and sale_is_block = 0 " +
                "group by sale_id_org) " +
                "order by Org_Name ";
        organizationArrayList = getOrganizationFromDbSelect(sql, null);
        return organizationArrayList;
    }

    public Organization getOrganization(int idOrganization) {
        ArrayList<Organization> organizationArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from organization where Org_Id=?";
        args.add(idOrganization);
        organizationArrayList = getOrganizationFromDbSelect(sql, args);
        if (organizationArrayList == null || organizationArrayList.size() == 0) return null;
        return organizationArrayList.get(0);
    }

    private ArrayList<Organization> getOrganizationFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> organizationListFromDb = null;
        organizationListFromDb = dbMvc.getSelectResult(sql, args);
        if (organizationListFromDb == null) return null;
        ArrayList<Organization> organizationArrayList = null;
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (Map row : organizationListFromDb) {
            if (organizationArrayList == null) organizationArrayList = new ArrayList<Organization>();
            Organization organization = new Organization();
            organizationArrayList.add(organization);
            Thread thread = new Thread(new GetOrganizationThread(organization, row));
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
        return organizationArrayList;
    }

    public String getListByFilter(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String response = "test ok";
        String filter = request.getParameter("filter");
        String idSelect = request.getParameter("idSelect").replace("_text", "");
        ArrayList<Organization> organizationArrayList = getOrganizationListByFilter(filter);
        if (organizationArrayList == null || organizationArrayList.size() == 0) {
            return "<select class=\"dropdown-menu\"  name=\"" + idSelect + "\" style=\"width: 150px\" required>\n" +
                    "                                <option></option>\n" +
                    "                        </select>";
        }
        response = "<select name=\"" + idSelect + "\" " +
                "id=\"" + idSelect + "\" " +
                "class=\"dropdown-menu\" onChange=\"getDogForOrg()\">";
        for (Organization organization : organizationArrayList) {
            response += "<option value=\"" + organization.getIdOrganization() + "\">" + organization.getOrganizationName() + "</option>";
        }
        response += "</select>";
        return response;
    }

    public ArrayList<Organization> getOrganizationListByFilter(String filter) {
        ArrayList<Organization> organizationArrayList = null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "select * from organization where Org_Name like ? order by Org_Name";
        args.add("%" + filter + "%");
        organizationArrayList = getOrganizationFromDbSelect(sql, args);
        return organizationArrayList;
    }

    public String getOrgDogById(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        String orgId = request.getParameter("orgId");
        if (orgId == null) return "не указана организация";
        ArrayList<OrganizationPact> organizationPactList = organizationPactMvc.getOrganizationPactList(orgId);
        if (organizationPactList == null) return "Договора не найдены";
        String response = "<select class=\"dropdown-menu\"  name=\"orgDogId\" id=\"orgDogId\" required onchange=\"getSumForDogForOrg()\"><option></option>";
        for (OrganizationPact organizationPact : organizationPactList) {
            response += "<option value=" + organizationPact.getId() + ">" + organizationPact.getPactName() + "</option>";
        }
        response += "</select>";
        return response;
    }

    private class GetOrganizationThread implements Runnable {
        Organization organization;
        Map row;

        public GetOrganizationThread(Organization organization, Map row) {
            this.organization = organization;
            this.row = row;
        }

        @Override
        public void run() {
            setOrganizationFromRow();
        }

        public void setOrganizationFromRow() {
            organization.setIdOrganization((Integer) row.get("Org_Id"));
            organization.setOrganizationName(row.get("Org_Name").toString());
            organization.setOrganizationPacts(organizationPactMvc.getOrganizationPactList(organization.getIdOrganization()));
        }
    }
}
