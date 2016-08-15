package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Organization {
    private int idOrganization;
    private String organizationName;

    @Autowired
    private DbJdbcModel dbMvc;

    public Organization() {
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
        }
    }
}
