package com.ccvisualizer.ccvisualizer;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Details {
    private String name = "--";
    private String username = "--";
    private String currRating = "--";
    private String maxRating = "--";
    private String currStar = "--";
    private String mxStar = "--";
    private String contestParticipated = "--";
    private String accepted = "--";
    private String wrong = "--";
    private String countryRank = "--";
    private String globalRank = "--";
    private String institute="--";
    private String country="INDIA";
    private String curr_col = "#11111";
    private String mx_col = "#111111";

    public String colorFind(String rating){
        Integer rate = Integer.parseInt(rating);
        if(rate < 1400) return "1★;#666666";
        else if(rate < 1600) return"2★;#1E7D22";
        else if(rate < 1800) return "3★;#3366CC";
        else if(rate < 2000) return "4★;#684273";
        else if(rate < 2200) return "5★;#FFBF00";
        else if(rate < 2500) return "6*;#FF7F00";
        else return "7*;#D0011B";
    }
}
