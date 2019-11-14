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

    public Position getMidPoint(){
        if(Math.abs(getDistance()) == 1){
            return null;
        }
        int mrow = getStart().getRow() + (getEnd().getRow() - getStart().getRow())/2;
        int mcell = getStart().getCell() + (getEnd().getCell() - getStart().getCell())/2;
        return new Position(mrow, mcell);
    }

    public String toString(){
        return "start:" + start + "\nend:" + end;
    }
}
