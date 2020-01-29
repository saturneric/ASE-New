package com.codesdream.ase.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_permit")
public class Permit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private String url;
    private int pid;

    public Permit(){

    }

    public Permit(String name, String description, String url, int pid) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public int getPid() {
        return pid;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permit permit = (Permit) o;
        return id == permit.id &&
                pid == permit.pid &&
                Objects.equals(name, permit.name) &&
                Objects.equals(description, permit.description) &&
                Objects.equals(url, permit.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, url, pid);
    }

    @Override
    public String toString() {
        return "Permit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                '}';
    }
}
