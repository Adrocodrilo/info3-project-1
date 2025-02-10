import java.io.File;
import java.util.LinkedList;
import java.util.Arrays;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Autograder{

    public static void main(String[] args ) throws Exception{
        String directory = "./tests";
        String solutionDirectory = "./solutions";
        if(args.length > 0){
            System.out.println("Utilizando directorio especificado para tests: '"+args[0]+"'");
            directory = args[0];
        }else{
            System.out.println("No se especifico directorio. Usando ruta por defecto: './tests' ");
        }
        if(args.length > 1){
            System.out.println("Utilizando directorio especificado para soluciones: '"+args[1]+"'");
            solutionDirectory = args[1];
        }else{
            System.out.println("No se especifico directorio. Usando ruta por defecto: './solutions' ");
        }
        File directoryFile = new File(directory);
        String[] files = directoryFile.list();
        System.out.println("El directorio de tests contiene "+files.length+" archivos");

        /*********************************************************************** */
        /****************No toque el codigo arriba de esta linea******************/
        /*********************************************************************** */
        /*La llamada al contructor. Envie aqui parametros adicionales si lo desea*/
        Solver solver = new Solver();
        /*********************************************************************** */
        /****************No toque el codigo abajo de esta linea ******************/
        /*********************************************************************** */

        double score = 0.0;
        double answerValue = 100.0 / files.length;
        for(int i = 0; i < files.length;i++){
            String filename = directory+"/"+files[i];
            String solutionFilename = solutionDirectory+"/solution-"+files[i];
            
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Analizando archivo '"+filename+"'");
            Maze maze = new Maze(filename);

            /*********************************************************************** */
            /**********************La llamada al metodo solve*************************/
            /*********************************************************************** */
            String obtained = solver.solve(maze);
            /*********************************************************************** */
            /*********************************************************************** */


            String expected = new String(Files.readAllBytes(Paths.get(solutionFilename)), StandardCharsets.UTF_8);
            System.out.println("Obtenido: " + obtained);
            System.out.println("Esperado: " + expected);
            score+= score(obtained, expected, answerValue);
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Puntaje Obtenido: "+score+"/100.00");
    }

    public static double score(String obtained, String expected, double answerValue){
        double score = 0;
        if(obtained.trim().equals(expected.trim())){
            score = answerValue;
        }
        System.out.println("Puntaje Obtenido: "+score+"/" + answerValue);
        return score;
    }
}