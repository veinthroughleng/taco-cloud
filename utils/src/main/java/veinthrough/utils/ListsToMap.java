package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
