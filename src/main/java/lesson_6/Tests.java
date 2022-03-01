package lesson_6;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tests {
    public static void main(String[] args) {
        test_9();

    }

    /**
     * Получить List чисел в виде текстовых элементов
     */
    public static void test_1() {
        List<Integer> integerList = getIntList();
        List<String> strings = integerList.stream().map(Objects::toString).toList();
        System.out.println(strings);
             }

    /**
     * Отсортировать список по убыванию
     */
    public static void test_2() {
        List<Integer> integerList = getIntList();
        List<Integer> sortedList = integerList.stream().sorted(Collections.reverseOrder()).toList();
        System.out.println(sortedList);
    }

    /**
     * Получить одну строку текста. Каждый элемент должен начинаться с текста "Number - ".
     * Элементы должны разделяться запятой и пробелом.
     * В начале итоговой строки должен быть текст "Number list: "
     * В конце итоговой строки должен быть текст "end of list.".
     */
    public static void test_3() {
        List<String> stringList = getStringList();
        String text = stringList.stream().map(item -> "Number - " + item).collect(Collectors.joining(", ","Number list: ", " end of list"));
        System.out.println(text);
    }

    /**
     * Получить мапу со значениями, ключи которых больше трех и меньше девяти
     */
    public static void test_4() {
        Map<Integer, String> map = getMap();
        Map<Integer, String> filterMap = map.entrySet().stream().filter(item -> item.getKey() > 3 && item.getKey() <  9)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(filterMap);
    }

    /**
     * Перемешать все элементы в мапе.
     * Пример результата:
     * Элемент 1: ключ - 5, значение "five"
     * Элемент 2: ключ - 7, значение "seven"
     * Элемент 3: ключ - 2, значение "two"
     * и так далее.
     */
    public static void test_5() {
        Map<Integer, String> map = getMap();
        ArrayList<Map.Entry<Integer, String>> sortList = new ArrayList<>(map.entrySet());
        Collections.shuffle(sortList);
        Map<Integer, String> newMap = new LinkedHashMap<>();
        sortList.forEach(item -> newMap.put(item.getKey(), item.getValue()));
        newMap.entrySet().forEach(item ->System.out.println("Ключ - "+item.getKey()+", "+"Значение "+item.getValue()));

    }

    /**
     * Установить во всех элементах isDisplayed = true, и оставить в списке только элементы с value NULL.
     */
    public static void test_6() {
        List<WebElement> elements = getElements();
        List<WebElement> filterElem = elements.stream().peek(item -> item.setDisplayed(true))
                .filter(item -> item.getValue() == null).toList();
        filterElem.stream().forEach(item -> System.out.println(item.isDisplayed()+", "+ item.getValue()));
    }

    /**
     * Инвертировать isDisplayed параметр каждого элемента и отсортировать список по типу элемента
     * со следующим приоритетом:
     * 1. TEXT
     * 2. INPUT_FIELD
     * 3. CHECKBOX
     * 4. BUTTON
     * 5. RADIO_BUTTON
     * 6. IMAGE
     */
    public static void test_7() {
        List<WebElement> elements = getElements();
        List<Type> sortList = new ArrayList<Type>();
        sortList.add(Type.TEXT);
        sortList.add(Type.INPUT_FIELD);
        sortList.add(Type.CHECKBOX);
        sortList.add(Type.BUTTON);
        sortList.add(Type.RADIO_BUTTON);
        sortList.add(Type.IMAGE);
        List<WebElement> newList = elements.stream().peek(item -> item.setDisplayed(!item.isDisplayed()))
                .sorted((item_1, item_2) -> {
                  int item1 = sortList.indexOf(item_1.getType());
                  int item2 = sortList.indexOf(item_2.getType());

                  int x = 0;
                  if (item1 > item2){
                      x = 1;
                  } else if (item1 < item2){
                      x = -1;
                  }
                  return x;
                }).toList();
        newList.stream().forEach(item -> System.out.println(item.getType()+", "+item.isDisplayed()));


    }

    /**
     * Создать мапу:
     * ключ - текст
     * значение - тип элемента
     */
    public static void test_8() {
        List<WebElement> elements = getElements();

        Map<String, Type> newMap = new HashMap<>();
        elements.stream().filter(item -> item.getText() != null).forEach(item -> newMap.put(item.getText(), item.getType()));
        newMap.entrySet().forEach(item -> System.out.println(item.getKey()+" "+item.getValue()));

    }

    /**
     * Получить список элементов, у которых текст или значение оканчивается на число от 500 и более.
     * И отсортировать по увеличению сначала элементы с текстом, а затем по убыванию элементы со значением.
     */
    public static void test_9() {
        List<WebElement> elements = getElements();
        List<WebElement> textElem = elements.stream().filter(item -> item.getText() != null)
                .filter(item -> Integer.parseInt(item.getText().substring(16))>= 500)
                .sorted((item_1, item_2) -> {
                   int x = 0;
                   if (Integer.parseInt(item_1.getText().substring(16)) > Integer.parseInt(item_2.getText().substring(16))){
                       x = 1;
                    } else if (Integer.parseInt(item_1.getText().substring(16)) < Integer.parseInt(item_2.getText().substring(16))){
                       x = -1;
                   }
                   return x;
                }).toList();
        List<WebElement> valueElem = elements.stream().filter(item -> item.getValue() != null)
                .filter(item -> Integer.parseInt(item.getValue().substring(17))>= 500)
                .sorted((item_1, item_2) -> {
                    int x = 0;
                    if (Integer.parseInt(item_1.getValue().substring(17)) < Integer.parseInt(item_2.getValue().substring(17))){
                        x = 1;
                    } else if (Integer.parseInt(item_1.getValue().substring(17)) > Integer.parseInt(item_2.getValue().substring(17))){
                        x = -1;
                    }
                    return x;
                })
                .toList();
        List<WebElement>allValue = new ArrayList<>();
        allValue.addAll(textElem);
        allValue.addAll(valueElem);
        allValue.stream().filter(item -> item.getText() != null).forEach(item -> System.out.println(item.getText()));
        allValue.stream().filter(item -> item.getValue() != null).forEach(item -> System.out.println(item.getValue()));

    }

    public static Map<Integer, String> getMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");
        return map;
    }

    public static List<String> getStringList() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");
        list.add("one");
        list.add("nine");
        list.add("ten");
        return list;
    }

    public static List<Integer> getIntList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        return list;
    }

    public static List<WebElement> getElements() {
        List<WebElement> result = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            result.add(new WebElement());
        }
        return result;
    }
}
