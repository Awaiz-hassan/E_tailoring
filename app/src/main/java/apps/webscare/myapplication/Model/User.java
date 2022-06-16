package apps.webscare.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fax")
    @Expose
    private Object fax;
    @SerializedName("propic")
    @Expose
    private String propic;
    @SerializedName("zip_code")
    @Expose
    private Object zipCode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("balance")
    @Expose
    private Object balance;
    @SerializedName("email_verified")
    @Expose
    private String emailVerified;
    @SerializedName("affilate_code")
    @Expose
    private Object affilateCode;
    @SerializedName("affilate_income")
    @Expose
    private Object affilateIncome;
    @SerializedName("affilate_link")
    @Expose
    private String affilateLink;
    @SerializedName("ban")
    @Expose
    private Object ban;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("package_end_date")
    @Expose
    private Object packageEndDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public Object getZipCode() {
        return zipCode;
    }

    public void setZipCode(Object zipCode) {
        this.zipCode = zipCode;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getBalance() {
        return balance;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public String getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Object getAffilateCode() {
        return affilateCode;
    }

    public void setAffilateCode(Object affilateCode) {
        this.affilateCode = affilateCode;
    }

    public Object getAffilateIncome() {
        return affilateIncome;
    }

    public void setAffilateIncome(Object affilateIncome) {
        this.affilateIncome = affilateIncome;
    }

    public String getAffilateLink() {
        return affilateLink;
    }

    public void setAffilateLink(String affilateLink) {
        this.affilateLink = affilateLink;
    }

    public Object getBan() {
        return ban;
    }

    public void setBan(Object ban) {
        this.ban = ban;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPackageEndDate() {
        return packageEndDate;
    }

    public void setPackageEndDate(Object packageEndDate) {
        this.packageEndDate = packageEndDate;
    }

}
