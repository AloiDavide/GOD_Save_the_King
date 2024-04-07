import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum Job {
    /*order of stats in the list:
    maxHP
    atk
    def
    speed
    moveRange
    atkRangeMax
    atkRangeMin
    */


    KNIGHT("Knight", List.of(
            15,
            7,
            5,
            6,
            5,
            1,
            1)),
    ARCHER("Archer", List.of(
            12,
            10,
            1,
            8,
            6,
            4,
            2)),
    BRIGAND("Brigand", List.of(
            20,
            10,
            2,
            3,
            4,
            1,
            1)),
    CASTER("Caster", List.of(
            20,
            6,
            3,
            2,
            4,
            2,
            1));


    public final String name;
    public final Map<String, Integer> baseStats;

    Job(String name, List<Integer> values){
        List<String> labels = List.of("maxHP","atk","def","speed","moveRange","atkRangeMax","atkRangeMin");
        this.name = name;
        this.baseStats = Collections.unmodifiableMap(IntStream.range(0,7).boxed().collect(Collectors.toMap(labels::get, values::get)));
    }

}
