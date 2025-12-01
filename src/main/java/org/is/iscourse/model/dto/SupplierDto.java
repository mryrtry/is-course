package org.is.iscourse.model.dto;

public class SupplierDto {
    private String name;
    private String contactInfo;
    private Integer leadTimeDays;

    public SupplierDto() {}
    
    public SupplierDto(String name) {
        this.name = name;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    
    public Integer getLeadTimeDays() { return leadTimeDays; }
    public void setLeadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; }
}