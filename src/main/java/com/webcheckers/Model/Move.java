package com.webcheckers.Model;

public class Move {
    Position start;
    Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }

    public int getDistance(){
        int yDist = 0;
        int xDist = 0;
        int dist = 0;
        yDist = end.getRow() - start.getRow();
        xDist = end.getCell() - start.getCell();

        /*
        if(xDist != yDist){
            dist = 0;
        }
        else{
            dist = xDist;
        }*/
        if(Math.abs(xDist) == Math.abs(yDist)){
            dist = yDist;
        }

        return dist;
    }

    public String toString(){
        return "start:" + start + "\nend:" + end;
    }
}
