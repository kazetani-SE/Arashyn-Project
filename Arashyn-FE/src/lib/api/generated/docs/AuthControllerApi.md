# AuthControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**completeRegister**](#completeregister) | **POST** /auth/complete-register | |
|[**initiateRegistration**](#initiateregistration) | **POST** /auth/verify | |
|[**login**](#login) | **POST** /auth/login | |
|[**logout**](#logout) | **POST** /auth/logout | |
|[**refresh**](#refresh) | **POST** /auth/refresh | |
|[**register**](#register) | **POST** /auth/register | |
|[**resendVerification**](#resendverification) | **POST** /auth/verify/resend | |
|[**verifyRegistration**](#verifyregistration) | **POST** /auth/verify/confirm | |

# **completeRegister**
> RegisterResponse completeRegister(completeRegisterRequest)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration,
    CompleteRegisterRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let completeRegisterRequest: CompleteRegisterRequest; //

const { status, data } = await apiInstance.completeRegister(
    completeRegisterRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **completeRegisterRequest** | **CompleteRegisterRequest**|  | |


### Return type

**RegisterResponse**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **initiateRegistration**
> initiateRegistration(registerVerifyRequest)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration,
    RegisterVerifyRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let registerVerifyRequest: RegisterVerifyRequest; //

const { status, data } = await apiInstance.initiateRegistration(
    registerVerifyRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **registerVerifyRequest** | **RegisterVerifyRequest**|  | |


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **login**
> LoginResponse login(loginRequest)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration,
    LoginRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let loginRequest: LoginRequest; //

const { status, data } = await apiInstance.login(
    loginRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **loginRequest** | **LoginRequest**|  | |


### Return type

**LoginResponse**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **logout**
> logout()


### Example

```typescript
import {
    AuthControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let refreshToken: string; // (optional) (default to undefined)

const { status, data } = await apiInstance.logout(
    refreshToken
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **refreshToken** | [**string**] |  | (optional) defaults to undefined|


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **refresh**
> LoginResponse refresh()


### Example

```typescript
import {
    AuthControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let refreshToken: string; // (optional) (default to undefined)

const { status, data } = await apiInstance.refresh(
    refreshToken
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **refreshToken** | [**string**] |  | (optional) defaults to undefined|


### Return type

**LoginResponse**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **register**
> RegisterResponse register(registerRequest)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration,
    RegisterRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let registerRequest: RegisterRequest; //

const { status, data } = await apiInstance.register(
    registerRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **registerRequest** | **RegisterRequest**|  | |


### Return type

**RegisterResponse**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **resendVerification**
> resendVerification(resendVerificationRequest)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration,
    ResendVerificationRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let resendVerificationRequest: ResendVerificationRequest; //

const { status, data } = await apiInstance.resendVerification(
    resendVerificationRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **resendVerificationRequest** | **ResendVerificationRequest**|  | |


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **verifyRegistration**
> verifyRegistration(verifyOtpRequest)


### Example

```typescript
import {
    AuthControllerApi,
    Configuration,
    VerifyOtpRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new AuthControllerApi(configuration);

let verifyOtpRequest: VerifyOtpRequest; //

const { status, data } = await apiInstance.verifyRegistration(
    verifyOtpRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **verifyOtpRequest** | **VerifyOtpRequest**|  | |


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

