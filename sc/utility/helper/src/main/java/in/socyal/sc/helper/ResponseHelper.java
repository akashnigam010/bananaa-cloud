package in.socyal.sc.helper;

import org.springframework.stereotype.Component;

import in.socyal.sc.helper.exception.ErrorCodesGettable;

@Component
public interface ResponseHelper {
	<T> T success(T response);

	<T> T failure(T response, boolean rollback);

	<T> T failure(T response, BusinessErrorCode businessErrorCode);

	<T> T failure(T response, BusinessErrorCode businessErrorCode, boolean rollback);

	<T> T failure(T response, Iterable<BusinessErrorCode> businessErrorCodes);

	<T> T failure(T response, Iterable<BusinessErrorCode> businessErrorCodes, boolean rollback);

	<T> T failure(T response, ErrorCodesGettable exception);
}
