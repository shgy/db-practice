为加深对数据库的理解，了解SQL调优的细节。 对presto的源码进行研究，探索Query Engine的实现细节。自行定义了project, 命名为db-practice。


v1.1版本的功能特性如下：
1. 基于代码生成技术实现SQL filter的功能。
2. SQL支持where 实现过滤整型字段，例如：select id, name from employee where id>1

文章组织如下：
1. 业务问题的背景
2. 基于Ast实现where
3. Ast直接实现的性能问题
4. 代码生成实现
5. 性能对比
6. 总结


presto中使用了基于ASM的airlift.bytecode进行代码生成，一个主要的用途是对从数据源捞上来的数据进行表达式过滤，
这是代码生成的主要应用场景，主要是为了降低进行表达式评估 中JVM 的各种开销，如虚函数调用，分支预测，原始类型的对象装箱开销以及内存消耗


# 处理细节
antlr4 -package org.example.antlr -no-listener -visitor .\SqlBase.g4

