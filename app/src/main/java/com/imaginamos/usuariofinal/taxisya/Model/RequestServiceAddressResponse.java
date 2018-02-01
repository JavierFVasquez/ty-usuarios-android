package com.imaginamos.usuariofinal.taxisya.Model;

/**
 * Created by javiervasquez on 1/12/17.
 */

public class RequestServiceAddressResponse {

    public Boolean success;
    public Data data;
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        public String id_servicio;
        public String id_conductor;
        public String id_vehiculo;
        public String latitude_orig;
        public String longitude_orig;
        public String latitude_des;
        public String longitude_des;
        public String barrio;
        public String obs;
        public String kind_id;
        public String fecha;
        public String nombre_usuario;
        public String direccion;
        public String id_tipo_pago;
        public String tipo_pago;
        public String vale;
        public String motivo;
        public String unidades;
        public String aeropuerto;
        public String nocturno;
        public String mensajeria;
        public String pp;
        public String codigo;
        public String km_recorrido;
        public Object tiempo;
        public String valor;
        private String estado;

        public String getId_servicio() {
            return id_servicio;
        }

        public String getId_conductor() {
            return id_conductor;
        }

        public String getId_vehiculo() {
            return id_vehiculo;
        }

        public String getLatitude_orig() {
            return latitude_orig;
        }

        public String getLongitude_orig() {
            return longitude_orig;
        }

        public String getLatitude_des() {
            return latitude_des;
        }

        public String getLongitude_des() {
            return longitude_des;
        }

        public String getBarrio() {
            return barrio;
        }

        public String getObs() {
            return obs;
        }

        public String getKind_id() {
            return kind_id;
        }

        public String getFecha() {
            return fecha;
        }

        public String getNombre_usuario() {
            return nombre_usuario;
        }

        public String getDireccion() {
            return direccion;
        }

        public String getId_tipo_pago() {
            return id_tipo_pago;
        }

        public String getTipo_pago() {
            return tipo_pago;
        }

        public String getVale() {
            return vale;
        }

        public String getMotivo() {
            return motivo;
        }

        public String getUnidades() {
            return unidades;
        }

        public String getAeropuerto() {
            return aeropuerto;
        }

        public String getNocturno() {
            return nocturno;
        }

        public String getMensajeria() {
            return mensajeria;
        }

        public String getPp() {
            return pp;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getKm_recorrido() {
            return km_recorrido;
        }

        public Object getTiempo() {
            return tiempo;
        }

        public String getValor() {
            return valor;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }



}
