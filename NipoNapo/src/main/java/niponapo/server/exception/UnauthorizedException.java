package niponapo.server.exception;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
		super("��ū�� ��ȿ���� �ʽ��ϴ�.\n�ٽ� �α��� ���ּ���.\n");
	}

}
