package in.socyal.sc.api.helper.exception;

import java.util.List;

public interface ErrorCodesGettable {
	Integer getErrorCode();

	List<Integer> getErrorCodes();
}
