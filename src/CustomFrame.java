import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CustomFrame extends JFrame {

    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 300;

    private ArrayList<Integer[]> edges;
    private ArrayList<Integer> passedSteps;
    private int degrees[];
    private int verticesNumber, startDegreeNumber, step, totalStepNumber;
    private double p1, p2;

    public CustomFrame(String appName){
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(appName);

        CustomPanel startDegreePanel = new CustomPanel("Start degree number");
        CustomPanel stepPanel = new CustomPanel("Step");
        CustomPanel totalStepsNumberPanel = new CustomPanel("Total steps number");
        CustomPanel degreePanel = new CustomPanel("Vertices number");

        JButton buttonMake = new JButton("Make");
        buttonMake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startDegreeNumber = (int)startDegreePanel.getValue();
                step = (int)stepPanel.getValue();
                totalStepNumber = (int)totalStepsNumberPanel.getValue();
                verticesNumber = (int)degreePanel.getValue();
                //p2 = p2panel.getValue();
                //verticesNumber = n1 + n2;

                edges = new ArrayList<>();
                passedSteps = new ArrayList<>();
                degrees = new int[verticesNumber * (totalStepNumber + 1) + 1];

                int currentDegree = 0, currentStepNumber = 0;

                while(currentDegree < startDegreeNumber){
                    if(addDoubleDegree(verticesNumber * (totalStepNumber + 1)) == 0){
                        currentDegree += 2;
                    }
                }

                while(currentStepNumber < totalStepNumber){
                    currentDegree = 0;
                    while(currentDegree < step){
                        if(addDoubleDegree(verticesNumber * (totalStepNumber + 1 - (currentStepNumber + 1))) == 0){
                            currentDegree += 2;
                        }
                    }
                    currentStepNumber++;
                }

                /*for(int i = 1; i < verticesNumber; i++){
                    for(int j = i + 1; j <= verticesNumber; j++)
                    if(probability > Math.random()){
                        edges.add(new Integer[]{i, j});
                        degrees[i]++;
                        degrees[j]++;
                    }
                }

                for(int i = 1; i < tempN; i++){
                    for(int j = i + 1; j <= tempN; j++)
                        if(probability > Math.random() && !isEdge(i, j)){
                            edges.add(new Integer[]{i, j});
                            degrees[i]++;
                            degrees[j]++;
                        }
                }*/

                //int currentDegree = 0;

                /*if(verticesNumber % 2 == 0) {
                    passedSteps.add(verticesNumber / 2);
                }

                while(currentDegree < d1 && currentDegree < d2){
                    if(addDoubleDegree(verticesNumber) == 0){
                        currentDegree += 2;
                    }
                }

                currentDegree = 0;

                while (currentDegree < Math.abs(d2 - d1)){
                    if(d2 - d1 > 0){
                        if(addDoubleDegree(n2) == 0){
                            currentDegree += 2;
                        }
                    }
                    else{
                        if(addDoubleDegree(n1) == 0){
                            currentDegree += 2;
                        }
                    }
                }

                if(degree % 2 != 0){
                    addDegreeToAllVertices();
                }*/

                writeEdges(edges);
                writeDegree(degrees);
                writeStatistic(degrees);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(startDegreePanel);
        panel.add(stepPanel);
        panel.add(totalStepsNumberPanel);
        panel.add(degreePanel);
        panel.add(buttonMake);

        add(panel);
    }

    public int addDoubleDegree(int verticesNumber){
        int step;
        Random random = new Random();

        step = random.nextInt((verticesNumber - 1)) + 1;
        System.out.println(step);

        if(passedSteps.contains(step)){
            return -1;
        }

        passedSteps.add(step);

        int verticesLinkedCount = 0, startingVertex = 1, j = 0;
        while (verticesLinkedCount != verticesNumber) {
            for (int i = startingVertex; j != startingVertex; i += step) {

                if (i > verticesNumber) {
                    i = i - verticesNumber;
                }
                j = i + step;
                if (j > verticesNumber) {
                    j = j - verticesNumber;
                }
                edges.add(new Integer[]{i, j});
                degrees[i]++;
                degrees[j]++;
                verticesLinkedCount++;
            }
            startingVertex++;
        }

        return 0;
    }

    public void addDegreeToAllVertices(){
        for(int i = 1; i <= verticesNumber / 2; i++){
            edges.add(new Integer[]{i, i + verticesNumber / 2});
            degrees[i]++;
            degrees[i + verticesNumber / 2]++;
        }
    }

    public void addDegreeToAllVertices(int verticesNumber, Random random){
        ArrayList<Integer> unconnectedVertices = new ArrayList<>();
        unconnectedVertices = newArrayList(unconnectedVertices, verticesNumber);
        ArrayList<Integer[]> tempEdges = new ArrayList<>();

        int count = 0;

        while(unconnectedVertices.size() > 0){

            int randomVertex1 = random.nextInt((unconnectedVertices.size()));;
            int randomVertex2 = random.nextInt((unconnectedVertices.size()));

            if(!unconnectedVertices.get(randomVertex1).equals(unconnectedVertices.get(randomVertex2))){

                if(!isEdge(unconnectedVertices.get(randomVertex1), unconnectedVertices.get(randomVertex2))) {
                    tempEdges.add(new Integer[]{unconnectedVertices.get(randomVertex1), unconnectedVertices.get(randomVertex2)});
                    degrees[unconnectedVertices.get(randomVertex1)]++;
                    degrees[unconnectedVertices.get(randomVertex2)]++;

                    Integer toRemove1, toRemove2;
                    toRemove1 = unconnectedVertices.get(randomVertex1);
                    toRemove2 = unconnectedVertices.get(randomVertex2);
                    unconnectedVertices.remove(toRemove1);
                    unconnectedVertices.remove(toRemove2);
                }
                else{
                    if(!isEdge(unconnectedVertices)){
                        System.out.println("restart");
                        for(int i = 1; i <= verticesNumber; i++){
                            if(i != unconnectedVertices.get(0) && i != unconnectedVertices.get(1)){
                                degrees[i]--;
                            }
                        }
                        addDegreeToAllVertices(verticesNumber, random);
                    }
                    System.out.println("count = " + ++count);
                }
            }
        }

        if(unconnectedVertices.size() == 0) {
            edges.addAll(tempEdges);
        }
    }

    public ArrayList<Integer> newArrayList(ArrayList<Integer> remainingNumbers, int verticesNumber){
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(Integer number: remainingNumbers){
            arrayList.add(number);
        }
        for(int i = 1; i <= verticesNumber; i++){
            arrayList.add(i);
        }

        return arrayList;
    }

    public boolean isEdge(ArrayList<Integer> arrayList){

        int count;
        for(int i = 0; i < arrayList.size() - 1; i++){
            for(int j = i + 1; j < arrayList.size(); j++){
                count = 0;
                for(Integer[] edge: edges){
                    if((edge[0].equals(arrayList.get(i)) && edge[1].equals(arrayList.get(j))) || (edge[0].equals(arrayList.get(j)) && edge[1].equals(arrayList.get(i)))){
                        break;
                    }
                    count++;
                }
                if(count == edges.size()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isEdge(int vertex1, int vertex2){
        for (Integer[] edge: edges){
            if((edge[0] == vertex1 && edge[1] == vertex2) || (edge[0] == vertex2 && edge[1] == vertex1)){
                return true;
            }
        }

        return false;
    }

    public void writeEdges(ArrayList<Integer[]> edges){

        try(FileWriter writer = new FileWriter("C:\\Users\\123\\Desktop\\edges.txt", false)) {

            for(Integer[] edge: edges) {
                String text = edge[0] + " " + edge[1];
                writer.write(text);
                writer.write("\r\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void writeDegree(int[] degrees){
        try(FileWriter writer = new FileWriter("C:\\Users\\123\\Desktop\\degrees.txt", false)) {
            for (int i = 1; i <= degrees.length - 1; i++) {
                String text = i + "(" + degrees[i] + ")";
                writer.write(text);
                writer.write("\r\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void writeStatistic(int[] degrees){
        try(FileWriter writer = new FileWriter("C:\\Users\\123\\Desktop\\statistic.txt", false)) {
            for (int i = 0; i <= degrees.length - 1; i++) {

                int nodesWithSameDegreeCount = 0;

                for(int j = 1; j < degrees.length; j++) {
                    if(degrees[j] == i){
                        nodesWithSameDegreeCount++;
                    }
                }

                String text = i + "(" + nodesWithSameDegreeCount + ")";
                writer.write(text);
                writer.write("\r\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void Task1(int degree, int verticesNumber){
        ArrayList<Integer> unconnectedVertices = new ArrayList<>();
        int totalDegree = 0, remainingVerticesCount;
        Random random = new Random();

        while (totalDegree != degree){

            remainingVerticesCount = unconnectedVertices.size();
            unconnectedVertices = newArrayList(unconnectedVertices, verticesNumber);

            int cycleCount = 0;

            while(unconnectedVertices.size() > 1){

                cycleCount++;

                int randomVertex1 = 0;
                int randomVertex2 = random.nextInt((unconnectedVertices.size()));

                if(remainingVerticesCount <= 0) {
                    randomVertex1 = random.nextInt((unconnectedVertices.size()));
                }

                System.out.println("first = " + randomVertex1 + ", second = " + randomVertex2);

                if(!unconnectedVertices.get(randomVertex1).equals(unconnectedVertices.get(randomVertex2))){

                    if(!isEdge(unconnectedVertices.get(randomVertex1), unconnectedVertices.get(randomVertex2))) {
                        edges.add(new Integer[]{unconnectedVertices.get(randomVertex1), unconnectedVertices.get(randomVertex2)});
                        degrees[unconnectedVertices.get(randomVertex1)]++;
                        degrees[unconnectedVertices.get(randomVertex2)]++;

                        remainingVerticesCount--;

                        Integer toRemove1, toRemove2;
                        toRemove1 = unconnectedVertices.get(randomVertex1);
                        toRemove2 = unconnectedVertices.get(randomVertex2);
                        unconnectedVertices.remove(toRemove1);
                        unconnectedVertices.remove(toRemove2);
                    }
                    else{
                        if(unconnectedVertices.size() == 2 || cycleCount > 1000){
                            break;
                        }
                    }
                }
            }

            totalDegree++;
        }
    }
}
