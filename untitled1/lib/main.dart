import 'package:flutter/material.dart';

void main() {
  runApp(const TodoApp());
}

class TodoApp extends StatelessWidget {
  const TodoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Todo List',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const TodoListScreen(),
    );
  }
}

class TodoListScreen extends StatefulWidget {
  const TodoListScreen({super.key});

  @override
  State<TodoListScreen> createState() => _TodoListScreenState();
}

class _TodoListScreenState extends State<TodoListScreen> {
  // 1. 할 일 목록을 저장할 리스트 만들기
  final List<String> _todoItems = [];

  // 2. 할 일을 추가하는 함수 만들기
  void _addTodoItem(String task) {
    if (task.isNotEmpty) {
      // setState를 호출해서 화면을 갱신해야 합니다.
      setState(() {
        _todoItems.add(task);
      });
    }
  }

  // 3. 화면 UI 만들기
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('My Todo List'),
      ),
      body: ListView.builder(
        itemCount: _todoItems.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text(_todoItems[index]),
          );
        },
      ),
      // TODO: 나중에 여기에 할 일을 추가하는 입력 필드와 버튼을 만들 예정입니다.
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          // 버튼을 눌렀을 때 할 일을 추가하는 기능 구현하기
          // 지금은 간단하게 "New Task"를 추가해봅시다.
          _addTodoItem('New Task ${_todoItems.length + 1}');
        },
        tooltip: 'Add task',
        child: const Icon(Icons.add),
      ),
    );
  }
}