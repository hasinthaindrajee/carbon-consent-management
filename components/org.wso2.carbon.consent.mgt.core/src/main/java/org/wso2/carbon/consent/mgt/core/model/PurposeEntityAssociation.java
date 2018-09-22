package org.wso2.carbon.consent.mgt.core.model;

public class PurposeEntityAssociation {

    private int associationId;
    private String purposeId;
    private String externalEntityName;
    private String externalEntityType;
    private int tenantId;

    public PurposeEntityAssociation(String purposeId, String externalEntityName, String externalEntityType, int tenantId) {

        this.purposeId = purposeId;
        this.externalEntityName = externalEntityName;
        this.externalEntityType = externalEntityType;
        this.tenantId = tenantId;

    }

    public PurposeEntityAssociation(int associationId, String purposeId, String externalEntityName, String
            externalEntityType, int tenantId) {

        this.associationId = associationId;
        this.purposeId = purposeId;
        this.externalEntityName = externalEntityName;
        this.externalEntityType = externalEntityType;
        this.tenantId = tenantId;

    }

    public String getPurposeId() {

        return purposeId;
    }

    public void setPurposeId(String purposeId) {

        this.purposeId = purposeId;
    }

    public String getExternalEntityName() {

        return externalEntityName;
    }

    public void setExternalEntityName(String externalEntityName) {

        this.externalEntityName = externalEntityName;
    }

    public String getExternalEntityType() {

        return externalEntityType;
    }

    public void setExternalEntityType(String externalEntityType) {

        this.externalEntityType = externalEntityType;
    }

    public int getAssociationId() {

        return associationId;
    }

    public void setAssociationId(int associationId) {

        this.associationId = associationId;
    }

    public int getTenantId() {

        return tenantId;
    }

    public void setTenantId(int tenantId) {

        this.tenantId = tenantId;
    }
}
