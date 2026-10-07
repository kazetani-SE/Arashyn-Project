# UserGrammarControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**_delete**](#_delete) | **DELETE** /user_grammar/{id} | |
|[**checkDuplicate**](#checkduplicate) | **POST** /user_grammar/check_duplicate | |
|[**checkDuplicateMultiple**](#checkduplicatemultiple) | **POST** /user_grammar/check_duplicate_multiple | |
|[**create**](#create) | **POST** /user_grammar | |
|[**detail**](#detail) | **GET** /user_grammar/{id} | |
|[**list**](#list) | **GET** /user_grammar | |
|[**update**](#update) | **PUT** /user_grammar | |

# **_delete**
> _delete()


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance._delete(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


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

# **checkDuplicate**
> UserGrammarDuplicateCheckResponse checkDuplicate(userGrammarDuplicateCheckRequest)


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration,
    UserGrammarDuplicateCheckRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

let userGrammarDuplicateCheckRequest: UserGrammarDuplicateCheckRequest; //

const { status, data } = await apiInstance.checkDuplicate(
    userGrammarDuplicateCheckRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userGrammarDuplicateCheckRequest** | **UserGrammarDuplicateCheckRequest**|  | |


### Return type

**UserGrammarDuplicateCheckResponse**

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

# **checkDuplicateMultiple**
> UserGrammarDuplicateCheckMultipleResponse checkDuplicateMultiple(userGrammarDuplicateCheckMultipleRequest)


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration,
    UserGrammarDuplicateCheckMultipleRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

let userGrammarDuplicateCheckMultipleRequest: UserGrammarDuplicateCheckMultipleRequest; //

const { status, data } = await apiInstance.checkDuplicateMultiple(
    userGrammarDuplicateCheckMultipleRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userGrammarDuplicateCheckMultipleRequest** | **UserGrammarDuplicateCheckMultipleRequest**|  | |


### Return type

**UserGrammarDuplicateCheckMultipleResponse**

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

# **create**
> UserGrammarIdResponse create(userGrammarCreateRequest)


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration,
    UserGrammarCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

let userGrammarCreateRequest: UserGrammarCreateRequest; //

const { status, data } = await apiInstance.create(
    userGrammarCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userGrammarCreateRequest** | **UserGrammarCreateRequest**|  | |


### Return type

**UserGrammarIdResponse**

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

# **detail**
> UserGrammarDetailResponse detail()


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.detail(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**UserGrammarDetailResponse**

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

# **list**
> UserGrammarListResponse list()


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

const { status, data } = await apiInstance.list();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**UserGrammarListResponse**

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

# **update**
> UserGrammarIdResponse update(userGrammarUpdateRequest)


### Example

```typescript
import {
    UserGrammarControllerApi,
    Configuration,
    UserGrammarUpdateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserGrammarControllerApi(configuration);

let userGrammarUpdateRequest: UserGrammarUpdateRequest; //

const { status, data } = await apiInstance.update(
    userGrammarUpdateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userGrammarUpdateRequest** | **UserGrammarUpdateRequest**|  | |


### Return type

**UserGrammarIdResponse**

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

