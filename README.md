

https://refactoringguru.cn/ru/design-patterns/strategy //паттерн стратегия (для алгоритмов)

https://habr.com/ru/articles/192102/   //генерация

1. Бэктрекинг (Backtracking)
2. Метод поэтапного исключения (Constraint Propagation)
3. Жадный алгоритм с приоритетами (Heuristic-based Search)
4. Алгоритм точного покрытия (Exact Cover) на основе метода Данцига (Dancing Links) (X алгоритм)

https://en.wikipedia.org/wiki/Sudoku_solving_algorithms //алгоритмы
https://habr.com/ru/companies/otus/articles/746408/ //бэктрекинг
    //методы поэтапного исключения:
    1. Naked Singles (Одиночка) - Когда в клетке остаётся только одно возможное значение.
    2. Hidden Singles (Скрытая одиночка) - Если число может быть поставлено только в одну клетку в строке, столбце или блоке, даже если у клетки есть несколько других кандидатов.
    3. Naked Pairs / Triplets (Голые пары / тройки) - Когда две клетки в строке/столбце/блоке содержат одинаковый набор из 2 возможных чисел (например, {2, 3}) — эти два числа не могут стоять ни в какой другой клетке этой группы.
    4. Pointing Pairs / Box-Line Reduction (Указующая пара) - Если в блоке 3x3 все кандидаты для числа находятся в одной строке или колонке, то это число можно исключить из этой строки/столбца вне блока.
https://www.cs.mcgill.ca/~aassaf9/python/algorithm_x.html //X алгоритм
https://www.cs.mcgill.ca/~aassaf9/python/sudoku.txt //X на python
https://disk.yandex.ru/i/RX0qZnxiBt4tZA //моя статья про X алгоритм

