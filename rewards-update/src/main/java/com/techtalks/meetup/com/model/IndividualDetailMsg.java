package com.techtalks.meetup.com.model;

/**
 * Created by tki214 on 3/10/17.
 */
public class IndividualDetailMsg {


    private String social;
    private String dob;
    private String name;


    public IndividualDetailMsg(String social, String dob, String name) {
        this.social = social;
        this.dob = dob;
        this.name = name;
    }


    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndividualDetailMsg that = (IndividualDetailMsg) o;

        if (!social.equals(that.social)) return false;
        if (!dob.equals(that.dob)) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = social.hashCode();
        result = 31 * result + dob.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

}
