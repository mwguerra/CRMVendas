package com.mwguerra.crmvendas;

public class Lead {
  protected Long id;
  protected String name;
  protected String email;
  protected String status;
  protected Integer score;
  private Boolean isVip;

  public Lead(Long id, String name, String email, String status, Integer score, Boolean isVip) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.status = status;
    this.score = score;
    this.isVip = isVip;
  }

  public String getName() {
    return this.name != null ? this.name : "";
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getScore() {
    return this.score != null ? this.score : 0;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Boolean getIs_vip() {
    return this.isVip;
  }

  public void setIs_vip(Boolean isVip) {
    this.isVip = isVip;
  }

  public String getEmail() {
    return this.email != null ? this.email : "";
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getVip() {
    return this.isVip;
  }

  public void setVip(Boolean vip) {
    isVip = vip;
  }

  @Override
  public String toString() {
    String vip = this.isVip ? "[VIP] " : "";
    String[] nameParts = this.name.split(" ");
    String firstName = nameParts[0];
    String emailParenthesis = " (" + this.email + ")";
    return vip + firstName + emailParenthesis + "\n>> " + this.status;
  }

  public String getStatus() {
    return status != null ? status : "";
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
