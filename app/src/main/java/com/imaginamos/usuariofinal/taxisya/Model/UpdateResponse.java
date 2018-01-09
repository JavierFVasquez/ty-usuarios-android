package com.imaginamos.usuariofinal.taxisya.Model;

public class UpdateResponse{
	private Object crtLat;
	private String createdAt;
	private Object pwdToken;
	private Object telephone;
	private Object crtLng;
	private String login;
	private String type;
	private String diageo;
	private int error;
	private String uuid;
	private String lastname;
	private String token;
	private String updatedAt;
	private String name;
	private String cellphone;
	private String id;
	private String email;

	public void setCrtLat(Object crtLat){
		this.crtLat = crtLat;
	}

	public Object getCrtLat(){
		return crtLat;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setPwdToken(Object pwdToken){
		this.pwdToken = pwdToken;
	}

	public Object getPwdToken(){
		return pwdToken;
	}

	public void setTelephone(Object telephone){
		this.telephone = telephone;
	}

	public Object getTelephone(){
		return telephone;
	}

	public void setCrtLng(Object crtLng){
		this.crtLng = crtLng;
	}

	public Object getCrtLng(){
		return crtLng;
	}

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setDiageo(String diageo){
		this.diageo = diageo;
	}

	public String getDiageo(){
		return diageo;
	}

	public void setError(int error){
		this.error = error;
	}

	public int getError(){
		return error;
	}

	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getUuid(){
		return uuid;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCellphone(String cellphone){
		this.cellphone = cellphone;
	}

	public String getCellphone(){
		return cellphone;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"UpdateResponse{" + 
			"crt_lat = '" + crtLat + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",pwd_token = '" + pwdToken + '\'' + 
			",telephone = '" + telephone + '\'' + 
			",crt_lng = '" + crtLng + '\'' + 
			",login = '" + login + '\'' + 
			",type = '" + type + '\'' + 
			",diageo = '" + diageo + '\'' + 
			",error = '" + error + '\'' + 
			",uuid = '" + uuid + '\'' + 
			",lastname = '" + lastname + '\'' + 
			",token = '" + token + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",cellphone = '" + cellphone + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}
