public aspect MyAspect{
	after() : execution(void Hello.main(String[])) {
		System.out.println("[After Hello]");
	}
}