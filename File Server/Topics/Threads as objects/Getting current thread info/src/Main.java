class Info {

    public static void printCurrentThreadInfo() {
        // get the thread and print its info
        Thread input = Thread.currentThread();

        System.out.println("name: " + input.getName());
        System.out.println("priority: " + input.getPriority());
    }
}