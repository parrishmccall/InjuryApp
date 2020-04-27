package com.example.injury3;

import java.util.Arrays;
import java.util.List;

public class Spinners {

    public List<String> caseTypes = Arrays.asList("Motor vehicle accidents",
            "Medical malpractice", "Product liability", "Premises liability",
            "Nursing home negligence", "Construction accidents", "Serious injuries",
            "Wrongful death");

    public List<String> userType = Arrays.asList("Plaintiff", "Attorney");

    public List getList(){
        return userType;
    }

}