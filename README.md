# SEG 16: Runway Re-declaration Tool
For our SEGP we are creating a Runway Re-declaration Tool using JavaFX.

## Installation
In order to compile and run the project, open the directory containing the pom.xlm and run the following command:

```bash
mvn clean javafx:run
```

While Developing the project, there is also the option to run with F5 (in vscode). However, this is a workaround and if it does not work or you need a foolproof way to run the project, use the above method.
It may ask which entry point to use. Launching this way you will need to select the Main class (App will not work).

## Testing
To run tests, go to the testing icon in the left hand pane (looks like a flask) then click the button at the top of the pane titled "run tests". Individual tests can also be run by clicking the run test icon next to the line of code.

We have also incorporated boundary testing, scenario testing as well as the unit testing.

Please tests before pushing to the github (as required).

## Packaging
Running the below command in the terminal will package the entire project into a shaded jar file which can be executed anywhere

```bash
mvn package
```

## How to use the application: 

Open the application 

Enter the TORA, TODA, ASDA, LDA, and displaced threshold of your runway alongside the designation. You can also import the runway data as an XML file from a selection of UK runways.

Either enter the parameters of the obstacle or import an XML file created by the ground team/pre-defined obstacles to populate the fields. 

Press “Calculate”.

Read the newly declared distances and look at the image. You can toggle between the top and side view of the runway. 

You will are able to rotate, zoom and move the runway. Rotation to match the orientation of the runway in the top-down view can be done using the checkbox.

To share the results, press export image and save on your device for onwards transmission. You can also export an obstacle or runway as an XML file for future reuse.

## Saving/Loading Runways and Obstacles
The saved object files are in XML, meaning they can be edited easily as a standalone file and does not require the software to be running. Below are examples of the two object's XML formats.

### Runway's XML Format
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<runway name="Birmingham International Airport" icao="EGBB">
        <mainDesignator>15</mainDesignator>
        <side>R</side>
        <takeOffLanding>TakeOff</takeOffLanding>
        <TORA>3003</TORA>
        <TODA>3063</TODA>
        <ASDA>3003</ASDA>
        <threshold>0</threshold>
        <LDA>3580</LDA>
        <RESA>0</RESA>
        <blastAllowance>0</blastAllowance>
    </runway>

```
### Obstacle's XML Format
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<obstacle>
    <name>Obstacle 1</name>
    <distanceX>250.0</distanceX>
    <distanceY>200.0</distanceY>
    <length>100.0</length>
    <width>75.0</width>
    <frontHeight>80.0</frontHeight>
    <backHeight>70.0</backHeight>
</obstacle>

```

### Obstacles (Multiple) XML Format
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<obstacles>
    <obstacle>
        <name>Obstacle 2</name>
        <distanceX>250.0</distanceX>
        <distanceY>200.0</distanceY>
        <length>100.0</length>
        <width>75.0</width>
        <frontHeight>80.0</frontHeight>
        <backHeight>70.0</backHeight>
    </obstacle>
</obstacles>

```

### Runways (Multiple) XML Format
``` xml
<runways>
    <runway name="Birmingham International Airport" icao="EGBB">
        <mainDesignator>15</mainDesignator>
        <side>R</side>
        <takeOffLanding>TakeOff</takeOffLanding>
        <TORA>3003</TORA>
        <TODA>3063</TODA>
        <ASDA>3003</ASDA>
        <threshold>0</threshold>
        <LDA>3580</LDA>
        <RESA>0</RESA>
        <blastAllowance>0</blastAllowance>
    </runway>
</runways>
```

## Zooming and Panning the Runway
The runway image can be zoomed and then panned to view finer details which may improve the usability of this product on smaller screen sizes, by those with visual difficulties, or simply use smaller obstacles.

Zooming can be acheived by scrolling through your normal system means. Panning can then be done through dragging or th equivalent trackpad gesture.

## Technical Architecture 

The application follows the MVC design pattern. The model is separated from the user interface (which has been designed in SceneBuilder and is represented in the FXML files). A controller interfaces between the backend model and the user interface. At this current early stage of the software’s development, there is quite a heavy reliance on the controller due to frequent interaction with the user interface.  

Increased separation and abstraction to increase cohesion remains a goal for the development team – namely building more model objects in order to make the expansion and maintenance of the project more viable in the future. 

The software makes use of the JavaFX framework to build the user interface and support many of the important functionalities of the program (for example, the image exporter that relies on the JavaFX snapshot). 

Our tool also incorporates XML compatability and this is validated against schemas to ensure correctness and reduce risk in use. Runways and obstacles can be both imported and exported, and they can also be done in groups.

The software remains expandable in the manner of adding multiple obstacles to the runway in future. Support will be easily extensible allowing for further improvement as the client demands. 

## Links
[Runway Re-declaration Tool](https://secure.ecs.soton.ac.uk/noteswiki/images/SEG_Runway_Project_DEFINITION_2015_Edited_v2.pdf)