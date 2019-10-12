package veinthrough.spring.tacocloud.data.model.converter;

import veinthrough.spring.tacocloud.util.Identifiable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListsToMap {
    public static <T extends Identifiable<T>> Map<?, List<T>> toIdentifiedMap(List<T> list) {
        return list.stream()
                .collect(Collectors.groupingBy(T::getIdentifier));
    }

    public static <T, K> Map<K, List<T>> toMap(List<T> list,
                                                Function<? super T, ? extends K> identifier) {
        return list.stream()
                .collect(Collectors.groupingBy(identifier));
    }
}
