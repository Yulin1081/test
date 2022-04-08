package uk.ac.soton.SEG16;

import uk.ac.soton.SEG16.runway.Runway;

public class Calculator {
     
    //diagram for each distance --> https://i.imgur.com/EOtWPRK.png

    private int oriLDA;
    private int oriASDA;
    private int oriTODA;
    private int oriTORA;
    private int reLDA;  //recalculated landing distancce available
    private int reASDA; //recalculated accelerate-stop distance
    private int reTODA; //recalculated take-off distance available
    private int reTORA; //recalculated take-off run available
    private int als;    //approach landding surface 
    private int resa;   //runway end safety area
    private Obstacle obstacle; //distance of obstacle from runway
    private int obsDistance;
    private int blastAllowance; //blast allowance for the specified air craft
    private int oriThreshold; 

    private String calcLDA;
    private String calcTODA;
    private String calcTORA;
    private String calcASDA;
    private String calcThreshold;


    public Calculator(Runway run){
        this.blastAllowance = run.getBlastAllowance();
        resa = run.getRESA();
        oriLDA = run.getLDA();
        oriASDA = run.getASDA();
        oriTORA = run.getTORA();
        oriTODA = run.getTODA();
        reLDA = oriLDA;
        reASDA = oriASDA;
        reTORA = oriTORA;
        reTODA = oriTODA;
        oriThreshold = run.getThreshold();
        obstacle = run.getObstacle();

        obsDistance = (int)Math.floor(obstacle.getDistanceX());
        if(obstacle.getFrontHeight() > obstacle.getBackHeight())
            obsDistance += obstacle.getLength();

        als = (int) (50 * Math.max( run.getObstacle().getBackHeight(), run.getObstacle().getFrontHeight() ));
        calcLDA = "unchanged";
        calcTORA = "unchanged";
        calcTODA = "unchanged";
        calcASDA = "unchanged";
        calcThreshold = "unchanged";
    }
    
    public void runCalculations(String side, boolean landing){
        String obstacleSide = obstacle.getDistanceX() < oriTORA/2 ? "left" : "right";
        boolean towards = obstacleSide == "right";
        runCalculations(towards, landing);
    }

    public void runCalculations(boolean towards, boolean landing){
        if(landing){
            calcLDA(towards);
        }
        else{
            calcTORA(towards);
        }
    }

    public void calcLDA (Boolean towards){
        if(towards) {
            calcLDAtowards();
         }
        else {
              calcLDAover();
         }
    }

    private void calcLDAover(){
        reLDA = oriLDA - obsDistance - Math.max(resa, als) - 60;
        calcLDA  = (oriLDA + " - " + obsDistance + " - " + Math.max(resa, als) + " - 60");
    }

    private void calcLDAtowards(){
        reLDA = obsDistance - resa - 60;
        calcLDA = (obsDistance + "-" + resa + " - 60");
    }
    
    public void calcTORA(boolean towards){
        if(towards){
            calcTORAtowards();
        }
        else{
            calcTORAaway();
        }
    }

    private void calcTORAtowards(){
        int subtractVal = Math.max(als, resa); //ALS/TOCS must be higher than RESA otherwise use RESA
        reTORA = obsDistance + oriThreshold - subtractVal - 60;
        reASDA = reTORA;
        reTODA = reTORA;
        calcTORA = (obsDistance + " + " + oriThreshold + " - " + subtractVal + " - 60");
        calcASDA = calcTORA;
        calcTODA = calcTORA;
    }

    private void calcTORAaway(){
        
        reTORA = oriTORA - obsDistance - blastAllowance - oriThreshold;
        reTODA = oriTODA - obsDistance - blastAllowance - oriThreshold;
        reASDA = oriASDA - obsDistance - blastAllowance - oriThreshold;
        calcTORA = (oriTORA + " - " + obsDistance + " - " + blastAllowance + " - " + oriThreshold);
        calcTODA = (oriTODA + " - " + obsDistance + " - " + blastAllowance) + " - " + oriThreshold;
        calcASDA = (oriASDA + " - " + obsDistance + " - " + blastAllowance + " - " + oriThreshold);
    }

    public int getTORA(){
        return reTORA;
    }

    public int getTODA(){
        return reTODA;
    }

    public int getASDA(){
        return reASDA;
    }

    public int getLDA(){
        return reLDA;
    }

    public String getCalcTORA(){
        return calcTORA;
    }
    
    public String getCalcTODA(){
        return calcTODA;
    }

    public String getCalcASDA(){
        return calcASDA;
    }

    public String getCalcLDA(){
        return calcLDA;
    }

    public String getCalcThreshold(){
        return calcThreshold;
    }
}
