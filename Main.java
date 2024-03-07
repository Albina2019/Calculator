import java.util.*;

//создаю класс main
public class Main {

    public static void main(String[] args) {

        System.out.print("Введите операцию с целыми числами от 1 до 10: "); // запрос ввода
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Решение задачи " + result); //выводим ответ
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //метод calc реализуем
    public static String calc(String input) throws Exception {
        Calculator calculator = new Calculator();
        return calculator.calculate(input);
    }
}

class Calculator {

    String calculate(String input) throws Exception {
        String[] arr;
        String operation;
        if (input.contains("+")) {
            arr = input.split("\\+");
            operation = "+";
        } else if (input.contains("-")) {
            arr = input.split("-");
            operation = "-";
        } else if (input.contains("*")) {
            arr = input.split("\\*");
            operation = "*";
        } else {
            arr = input.split("/");
            operation = "/";
        }

        if (arr.length < 2) {
            throw new Exception("throws Exception //т.к. строка не является математической операцией");
        } else if (arr.length > 2) {
            throw new Exception("throws Exception //Знак операции встречается более 1 раза!");
        } else {
            arr[0] = arr[0].trim();
            arr[1] = arr[1].trim();
            int a, b;
            boolean roman;
            try {
                a = Integer.valueOf(arr[0]); // пытаемся распарсить как арабское число
                roman = false;
            } catch (NumberFormatException ex) {
                a = romanToArab(arr[0]); // пытаемся распарсить как римское
                roman = true;
            }
            try {
                b = Integer.valueOf(arr[1]); // пытаемся распарсить как арабское число
            } catch (NumberFormatException ex) {
                b = romanToArab(arr[1]); // пытаемся распарсить как римское
            }
            return calculate(a, b, operation, roman);
        }
    }

    private String calculate(int a, int b, String operation, boolean roman) throws Exception {
        if ((a < 1) || (a > 10) || (b < 1) || (b > 10)) { //ограничения чисел (диапазон)
            throw new Exception("throws Exception //Числа выходят за рамки диапазона!");
        }

        int result;
        switch (operation) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            default:
                result = a / b;
        }

        return numberToString(result, roman);
    }

    // подготовка результата (result) в виде строки в зависимости от того, римское оно или арабское
    private String numberToString(int result, boolean roman) throws Exception {
        String output;//вывод
        if (roman) {
            if (result < 1) {
                throw new Exception("throws Exception //Римские числа бывают только положительными");
            } else {
                output = arabToRome(result);
            }
        } else {
            output = result + "";
        }

        return output;
    }

    private Integer romanToArab(String romanInput) throws Exception {// переводим римский ввод в арабский
        int result = 0;
        int[] arab = {10, 9, 5, 4, 1};
        String[] roman = {"X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arab.length; i++) {
            while (romanInput.indexOf(roman[i]) == 0) {
                result += arab[i];
                romanInput = romanInput.substring(roman[i].length());// исключаем посчитанные числа
            }
        }

        if (result == 0) {
            throw new Exception("throws Exception //Число некорректно!");
        }

        return result;
    }

    private String arabToRome(int arabInput) {// перевод арабских в римские
        String result = "";
        int value = 0;
        int[] arab = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        for (int i = 0; i < arab.length; i++) {
            value = arabInput / arab[i];
            for (int j = 0; j < value; j++) {
                result = result.concat(roman[i]);
            }
            arabInput = arabInput % arab[i];
        }
        return result;
    }
}