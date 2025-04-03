package com.hooniegit.SourceData.Tag;

import java.lang.reflect.Array;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 데이터 그룹 클래스입니다. TagData 객체를 배열로 보관하며, 타임스탬프 정보를 포함합니다.
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TagGroup<T> {

    private String timestamp;
    private T[] group;

    /**
     * 제네릭 배열을 n개로 분할합니다.
     * @param array
     * @param n
     * @return
     * @throws Exception
     */
    public T[][] splitArray(int n) throws Exception {

        int totalLength = this.group.length;
        int baseSize = totalLength / n;
        int remainder = totalLength % n;

        // Java에서는 제네릭 배열을 직접 생성할 수 없기 때문에 리플렉션 사용
        @SuppressWarnings("unchecked")
        T[][] result = (T[][]) Array.newInstance(this.group.getClass().getComponentType(), n, 0);

        int start = 0;
        for (int i = 0; i < n; i++) {
            int partSize = baseSize + (i < remainder ? 1 : 0);
            T[] part = Arrays.copyOfRange(this.group, start, start + partSize);
            result[i] = part;
            start += partSize;
        }

        return result;
    }

}
