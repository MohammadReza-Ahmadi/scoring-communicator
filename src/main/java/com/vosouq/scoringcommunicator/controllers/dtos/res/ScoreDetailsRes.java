package com.vosouq.scoringcommunicator.controllers.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreDetailsRes {
    private List<ChartItem> chartItems;
    private Percentile percentile;
    private List<Detail> details;

    public void addNewChartItem(Integer x, Integer y, String color) {
        getChartItems().add(new ChartItem(x, y, color));
    }

    public void addNewDetail(String title, Integer score, Integer maxScore) {
        getDetails().add(new Detail(title, score, maxScore));
    }

    public void setPercentileData(Integer total, Integer level) {
        percentile = new Percentile();
        percentile.setTotal(total);
        percentile.setLevel(level);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class ChartItem {
        private Integer x; // score(up-to)
        private Integer y; // count of users
        private String color; // match score bar color one of (5 classified colors) others=gray
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class Percentile {
        private int total;
        private int level;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class Detail {
        private String title;
        private Integer score;
        private Integer maxScore;
    }
}
