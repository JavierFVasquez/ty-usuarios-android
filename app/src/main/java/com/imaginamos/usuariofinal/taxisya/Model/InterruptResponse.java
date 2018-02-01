package com.imaginamos.usuariofinal.taxisya.Model;


import java.util.ArrayList;
import java.util.List;

public class InterruptResponse
{
    private boolean success;
    private Data data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public class Data {
        public String id;
        public String user_id;
        public Object driver_id;
        public Object car_id;
        public String from_lat;
        public String from_lng;
        public String status_id;
        public Object to_lat;
        public Object to_lng;
        public Object start_datetime;
        public Object finish_datetime;
        public Object qualification;
        public Object reason_qualification;
        public String index_id;
        public String comp1;
        public String comp2;
        public String no;
        public String barrio;
        public String obs;
        public String kind_id;
        public String schedule_id;
        public String schedule_type;
        public String updated_at;
        public String created_at;
        public String destination;
        public Object service_date_time;
        public Object user_name;
        public String address;
        public String cms_users_id;
        public String pay_type;
        public String pay_reference;
        public String user_card_reference;
        public String user_email;
        public String units;
        public String charge1;
        public String charge2;
        public String charge3;
        public String charge4;
        public String value;
        public String code;
        public String company_id;
        public Object state_payment;
        public String cedula;
        public String date_state_payment;
        public String commit;
        public String facturado;
        public Object km_recorrido;
        public Object tiempo_recorido;
        public Object valor_app;
        public Object n_pasajeros;
        public Object id_carrera_ant;
        public Object valor_total;



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Object getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(Object driver_id) {
            this.driver_id = driver_id;
        }

        public Object getCar_id() {
            return car_id;
        }

        public void setCar_id(Object car_id) {
            this.car_id = car_id;
        }

        public String getFrom_lat() {
            return from_lat;
        }

        public void setFrom_lat(String from_lat) {
            this.from_lat = from_lat;
        }

        public String getFrom_lng() {
            return from_lng;
        }

        public void setFrom_lng(String from_lng) {
            this.from_lng = from_lng;
        }

        public String getStatus_id() {
            return status_id;
        }

        public void setStatus_id(String status_id) {
            this.status_id = status_id;
        }

        public Object getTo_lat() {
            return to_lat;
        }

        public void setTo_lat(Object to_lat) {
            this.to_lat = to_lat;
        }

        public Object getTo_lng() {
            return to_lng;
        }

        public void setTo_lng(Object to_lng) {
            this.to_lng = to_lng;
        }

        public Object getStart_datetime() {
            return start_datetime;
        }

        public void setStart_datetime(Object start_datetime) {
            this.start_datetime = start_datetime;
        }

        public Object getFinish_datetime() {
            return finish_datetime;
        }

        public void setFinish_datetime(Object finish_datetime) {
            this.finish_datetime = finish_datetime;
        }

        public Object getQualification() {
            return qualification;
        }

        public void setQualification(Object qualification) {
            this.qualification = qualification;
        }

        public Object getReason_qualification() {
            return reason_qualification;
        }

        public void setReason_qualification(Object reason_qualification) {
            this.reason_qualification = reason_qualification;
        }

        public String getIndex_id() {
            return index_id;
        }

        public void setIndex_id(String index_id) {
            this.index_id = index_id;
        }

        public String getComp1() {
            return comp1;
        }

        public void setComp1(String comp1) {
            this.comp1 = comp1;
        }

        public String getComp2() {
            return comp2;
        }

        public void setComp2(String comp2) {
            this.comp2 = comp2;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getBarrio() {
            return barrio;
        }

        public void setBarrio(String barrio) {
            this.barrio = barrio;
        }

        public String getObs() {
            return obs;
        }

        public void setObs(String obs) {
            this.obs = obs;
        }

        public String getKind_id() {
            return kind_id;
        }

        public void setKind_id(String kind_id) {
            this.kind_id = kind_id;
        }

        public String getSchedule_id() {
            return schedule_id;
        }

        public void setSchedule_id(String schedule_id) {
            this.schedule_id = schedule_id;
        }

        public String getSchedule_type() {
            return schedule_type;
        }

        public void setSchedule_type(String schedule_type) {
            this.schedule_type = schedule_type;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Object getService_date_time() {
            return service_date_time;
        }

        public void setService_date_time(Object service_date_time) {
            this.service_date_time = service_date_time;
        }

        public Object getUser_name() {
            return user_name;
        }

        public void setUser_name(Object user_name) {
            this.user_name = user_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCms_users_id() {
            return cms_users_id;
        }

        public void setCms_users_id(String cms_users_id) {
            this.cms_users_id = cms_users_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_reference() {
            return pay_reference;
        }

        public void setPay_reference(String pay_reference) {
            this.pay_reference = pay_reference;
        }

        public String getUser_card_reference() {
            return user_card_reference;
        }

        public void setUser_card_reference(String user_card_reference) {
            this.user_card_reference = user_card_reference;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

        public String getCharge1() {
            return charge1;
        }

        public void setCharge1(String charge1) {
            this.charge1 = charge1;
        }

        public String getCharge2() {
            return charge2;
        }

        public void setCharge2(String charge2) {
            this.charge2 = charge2;
        }

        public String getCharge3() {
            return charge3;
        }

        public void setCharge3(String charge3) {
            this.charge3 = charge3;
        }

        public String getCharge4() {
            return charge4;
        }

        public void setCharge4(String charge4) {
            this.charge4 = charge4;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public Object getState_payment() {
            return state_payment;
        }

        public void setState_payment(Object state_payment) {
            this.state_payment = state_payment;
        }

        public String getCedula() {
            return cedula;
        }

        public void setCedula(String cedula) {
            this.cedula = cedula;
        }

        public String getDate_state_payment() {
            return date_state_payment;
        }

        public void setDate_state_payment(String date_state_payment) {
            this.date_state_payment = date_state_payment;
        }

        public String getCommit() {
            return commit;
        }

        public void setCommit(String commit) {
            this.commit = commit;
        }

        public String getFacturado() {
            return facturado;
        }

        public void setFacturado(String facturado) {
            this.facturado = facturado;
        }

        public Object getKm_recorrido() {
            return km_recorrido;
        }

        public void setKm_recorrido(Object km_recorrido) {
            this.km_recorrido = km_recorrido;
        }

        public Object getTiempo_recorido() {
            return tiempo_recorido;
        }

        public void setTiempo_recorido(Object tiempo_recorido) {
            this.tiempo_recorido = tiempo_recorido;
        }

        public Object getValor_app() {
            return valor_app;
        }

        public void setValor_app(Object valor_app) {
            this.valor_app = valor_app;
        }

        public Object getN_pasajeros() {
            return n_pasajeros;
        }

        public void setN_pasajeros(Object n_pasajeros) {
            this.n_pasajeros = n_pasajeros;
        }

        public Object getId_carrera_ant() {
            return id_carrera_ant;
        }

        public void setId_carrera_ant(Object id_carrera_ant) {
            this.id_carrera_ant = id_carrera_ant;
        }

        public Object getValor_total() {
            return valor_total;
        }

        public void setValor_total(Object valor_total) {
            this.valor_total = valor_total;
        }

    }
}