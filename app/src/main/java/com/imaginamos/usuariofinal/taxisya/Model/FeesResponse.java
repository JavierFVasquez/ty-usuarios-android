package com.imaginamos.usuariofinal.taxisya.Model;

public class FeesResponse{
	private String minima;
	private String km;
	private int total_recargo;
	private String nocturno_calidad;
	private String km_calidad;
	private String banderazo_calidad;
	private String puerta_a_puerta;
	private String puerta_a_puerta_calidad;
	private String minima_calidad;
	private String nocturno;
	private String banderazo;
	private int total_recargo_calidad;
	private String aeropuerto;

	public void setMinima(String minima){
		this.minima = minima;
	}

	public String getMinima(){
		return minima;
	}

	public void setKm(String km){
		this.km = km;
	}

	public String getKm(){
		return km;
	}

	public void setTotal_recargo(int total_recargo){
		this.total_recargo = total_recargo;
	}

	public int getTotal_recargo(){
		return total_recargo;
	}

	public void setNocturno_calidad(String nocturno_calidad){
		this.nocturno_calidad = nocturno_calidad;
	}

	public String getNocturno_calidad(){
		return nocturno_calidad;
	}

	public void setKm_calidad(String km_calidad){
		this.km_calidad = km_calidad;
	}

	public String getKm_calidad(){
		return km_calidad;
	}

	public void setBanderazo_calidad(String banderazo_calidad){
		this.banderazo_calidad = banderazo_calidad;
	}

	public String getBanderazo_calidad(){
		return banderazo_calidad;
	}

	public void setPuerta_a_puerta(String puerta_a_puerta){
		this.puerta_a_puerta = puerta_a_puerta;
	}

	public String getPuerta_a_puerta(){
		return puerta_a_puerta;
	}

	public void setPuerta_a_puerta_calidad(String puerta_a_puerta_calidad){
		this.puerta_a_puerta_calidad = puerta_a_puerta_calidad;
	}

	public String getPuerta_a_puerta_calidad(){
		return puerta_a_puerta_calidad;
	}

	public void setMinima_calidad(String minima_calidad){
		this.minima_calidad = minima_calidad;
	}

	public String getMinima_calidad(){
		return minima_calidad;
	}

	public void setNocturno(String nocturno){
		this.nocturno = nocturno;
	}

	public String getNocturno(){
		return nocturno;
	}

	public void setBanderazo(String banderazo){
		this.banderazo = banderazo;
	}

	public String getBanderazo(){
		return banderazo;
	}

	public void setTotal_recargo_calidad(int total_recargo_calidad){
		this.total_recargo_calidad = total_recargo_calidad;
	}

	public int getTotal_recargo_calidad(){
		return total_recargo_calidad;
	}

	@Override
 	public String toString(){
		return 
			"FeesResponse{" + 
			"minima = '" + minima + '\'' + 
			",km = '" + km + '\'' + 
			",total_recargo = '" + total_recargo + '\'' +
			",nocturno_calidad = '" + nocturno_calidad + '\'' +
			",km_calidad = '" + km_calidad + '\'' +
			",banderazo_calidad = '" + banderazo_calidad + '\'' +
			",puerta_a_puerta = '" + puerta_a_puerta + '\'' +
			",puerta_a_puerta_calidad = '" + puerta_a_puerta_calidad + '\'' +
			",minima_calidad = '" + minima_calidad + '\'' +
			",nocturno = '" + nocturno + '\'' + 
			",banderazo = '" + banderazo + '\'' + 
			",total_recargo_calidad = '" + total_recargo_calidad + '\'' +
			"}";
		}

	public String getAeropuerto() {
		return aeropuerto;
	}

	public void setAeropuerto(String aeropuerto) {
		this.aeropuerto = aeropuerto;
	}
}
