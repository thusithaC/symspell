This is a reimplementation of
[Faroo's symmetric deletion algorithm for spelling correction](https://github.com/wolfgarbe/symspell).
It is written in Scala and will diverge considerably from the original. It aims to solve a related but different problem:
Spelling correction for company names. Company names are much longer that typical dictionary words which leads to a lot
of problems with this algorithm.

Here is some example data for a deletion distance of **4**

Dictionary entry | Number of deletions | Time for generation (ms)
-----|-------------------|----------
technology | 356 | 130
China High-Speed Railway Technology | 53018 | 79253

This is a toy project at the moment. I'm exploring how to write the algorithm in a functional manner.