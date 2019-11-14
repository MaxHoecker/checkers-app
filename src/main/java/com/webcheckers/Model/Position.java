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

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Position)){
            return false;
        }
        Position o = (Position)other;
        return this.getRow().equals(o.getRow()) && this.getCell().equals(getCell());
    }

    public String toString(){
        return "["+row+","+cell+"]";
    }
}
