import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Opn {

    /**
     * @param args Параметры с консоли
     */
    public static void main(String[] args) {
        final Evaluator evaluator = new Evaluator();
        final double res = evaluator.evaluate("5 1 2 + 4 * + 3 -");

        System.out.println(res);
    }

    private static class Evaluator {

        /**
         * Карта операций, чтобы можно было доставать операции
         * по их символу.
         */
        private final static Map<String, Operations> operations = new HashMap<>();

        static {
            for (Operations op : Operations.values()) {
                operations.put(op.operationName(), op);
            }
        }

        /**
         * Считает выражение обратной польской нотации.
         *
         * @param expr Выражение обратной польской нотации, должно
         *             быть валидным
         * @return Результат вычисления
         */
        double evaluate(final String expr) {
            final Stack<Integer> stack = new Stack<>();

            for (String lexem : expr.split(" ")) {
                try {
                    processLexem(lexem, stack);
                } catch (Exception e) {
                    return 0;
                }
            }

            return Double.valueOf(stack.peek());
        }

        /**
         * Обрабатывает лексему из строки переданной пользователем.
         *
         * @param lexem Лексема которую надо разобрать
         * @param stack Стек в котором все это делаем
         */
        private void processLexem(final String lexem, final Stack<Integer> stack) {
            if (operations.containsKey(lexem)) {
                calculateExpression(lexem, stack);

                return;
            }

            stack.push(Integer.valueOf(lexem));
        }

        /**
         * Выполняет операцию {@literal + / - *} над двумя числами из стека.
         *
         * @param operation Операция которую нужно выполнить над двумя числами
         * @param stack     Стек в котором это нужно сделать
         */
        private void calculateExpression(final String operation, final Stack<Integer> stack) {
            final int a = stack.pop();
            final int b = stack.pop();

            Operations op = operations.get(operation);
            stack.push(op.process(a, b));
        }

        /**
         * Операции ОПН
         *
         * @since 0.0.1
         */
        private enum Operations {

            /**
             * Операция сложения ОПН.
             */
            SUM("+") {
                /**
                 * Вычисляет сумму двух переданных чисел.
                 *
                 * @param a Первое число для суммирования
                 * @param b Второе число для суммирования
                 * @return Сумма двух чисел
                 */
                @Override
                int process(final int a, final int b) {
                    return a + b;
                }
            },

            /**
             * Операция деления ОПН.
             */
            DIVIDE("/") {
                /**
                 * Вычисляет частное для двух членов.
                 *
                 * @param op1 Делимое
                 * @param op2 Делитель
                 * @return Частное
                 */
                @Override
                int process(final int op1, final int op2) {
                    return op1 / op2;
                }
            },

            /**
             * Вычисляет разницу ОПН.
             */
            MINUS("-") {
                /**
                 * Считает разницу между двумя числами.
                 *
                 * @param op1 Первое число
                 * @param op2 Второе число
                 * @return Разница между членами
                 */
                @Override
                int process(final int op1, final int op2) {
                    return op2 - op1;
                }
            },

            /**
             * Произведение ОПН.
             */
            MUL("*") {
                /**
                 * Вычисляет произведение между двумя числами.
                 *
                 * @param op1 Первое число
                 * @param op2 Второе число
                 * @return Произведение
                 */
                @Override
                int process(final int op1, final int op2) {
                    return op1 * op2;
                }
            };

            /**
             * Символ операции в строке
             */
            private final String name;

            /**
             * @param name Имя операции перечисления
             */
            Operations(final String name) {
                this.name = name;
            }

            /**
             * @return Имя операции перечесления
             */
            String operationName() {
                return name;
            }

            /**
             * Выполнить операцию над двумя числами.
             *
             * @param op1 Первое число
             * @param op2 Второе число
             * @return Результат выполнения числа
             */
            abstract int process(final int op1, final int op2);
        }
    }
}
