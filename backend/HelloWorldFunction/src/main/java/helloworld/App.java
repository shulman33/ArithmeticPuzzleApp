package helloworld;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import static helloworld.GeneticAlgorithmConfig.SelectionType.TOURNAMENT;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final GeneticAlgorithmConfig.SelectionType selectionMethod = TOURNAMENT;

    private static String basicRun(String augend, String addend, String sum,
                                 double mutationProbability, double crossoverProbability, int POPULATION_SIZE, int MAX_GENERATIONS){


        GeneticAlgorithmConfig gac = new GeneticAlgorithmConfig(POPULATION_SIZE, MAX_GENERATIONS, selectionMethod, mutationProbability, crossoverProbability);
        ArithmeticPuzzleBase apb = new ArithmeticPuzzle(augend, addend, sum);
        ArithmeticPuzzleBase.SolutionI solution = apb.solveIt(gac);
//        System.out.println(solution.toString());
        return solution.toString();
    }

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        final String augend = input.getQueryStringParameters().get("augend");
        final String addend = input.getQueryStringParameters().get("addend");
        final String sum = input.getQueryStringParameters().get("sum");


        final double MUTATION_PROBABILITY = input.getQueryStringParameters().get("mutationProbability") == null ? 0.15 : Double.parseDouble(input.getQueryStringParameters().get("mutationProbability"));
        final double CROSSOVER_PROBABILITY = input.getQueryStringParameters().get("crossoverProbability") == null ? 0.7 : Double.parseDouble(input.getQueryStringParameters().get("crossoverProbability"));
        final int POPULATION_SIZE = input.getQueryStringParameters().get("populationSize") == null ? 100 : Integer.parseInt(input.getQueryStringParameters().get("populationSize"));
        final int MAX_GENERATIONS = input.getQueryStringParameters().get("maxGenerations") == null ? 100 : Integer.parseInt(input.getQueryStringParameters().get("maxGenerations"));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        return response
                .withStatusCode(200)
                .withBody(basicRun(augend, addend, sum, MUTATION_PROBABILITY, CROSSOVER_PROBABILITY, POPULATION_SIZE, MAX_GENERATIONS));

    }

}
