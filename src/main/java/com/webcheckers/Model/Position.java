package com.webcheckers.Model;

public class Position {
    private int row;
    private int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public Integer getRow(){
        return row;
    }

    public Integer getCell(){
        return cell;
    }

    public String toString(){
        return "["+row+","+cell+"]";
    }
}
