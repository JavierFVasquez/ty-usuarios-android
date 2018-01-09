package com.imaginamos.usuariofinal.taxisya.Model;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse{

	@SerializedName("crt_lat")
	private Object crtLat;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("pwd_token")
	private Object pwdToken;

	@SerializedName("telephone")
	private Object telephone;

	@SerializedName("crt_lng")
	private Object crtLng;

	@SerializedName("login")
	private String login;

	@SerializedName("type")
	private String type;

	@SerializedName("diageo")
	private String diageo;

	@SerializedName("error")
	private int error;

	@SerializedName("uuid")
	private String uuid;

	@SerializedName("lastname")
	private String lastname;

	@SerializedName("token")
	private String token;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("cellphone")
	private String cellphone;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	public Object getCrtLat(){
		return crtLat;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getPwdToken(){
		return pwdToken;
	}

	public Object getTelephone(){
		return telephone;
	}

	public Object getCrtLng(){
		return crtLng;
	}

	public String getLogin(){
		return login;
	}

	public String getType(){
		return type;
	}

	public String getDiageo(){
		return diageo;
	}

	public int getError(){
		return error;
	}

	public String getUuid(){
		return uuid;
	}

	public String getLastname(){
		return lastname;
	}

	public String getToken(){
		return token;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public String getCellphone(){
		return cellphone;
	}

	public String getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}
}