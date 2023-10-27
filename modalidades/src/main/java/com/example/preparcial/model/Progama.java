package com.example.preparcial.model;

import java.io.Serializable;

    public class Progama implements Serializable {
        private String codigo;
        private String nombre;
        private String modalidad;

        public Progama() {
        }

        public Progama(String codigo, String nombre, String modalidad) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.modalidad = modalidad;
        }

        public String getCodigo() {
            return this.codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return this.nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getModalidad() {
            return this.modalidad;
        }

        public void setModalidad(String modalidad) {
            this.modalidad = modalidad;
        }
}
