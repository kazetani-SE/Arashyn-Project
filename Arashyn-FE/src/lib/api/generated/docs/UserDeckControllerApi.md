# UserDeckControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**checkDuplicate2**](#checkduplicate2) | **POST** /user_deck/check_duplicate | |
|[**clone1**](#clone1) | **POST** /user_deck/clone | |
|[**create2**](#create2) | **POST** /user_deck | |
|[**delete2**](#delete2) | **DELETE** /user_deck/{id} | |
|[**detail2**](#detail2) | **GET** /user_deck/{id} | |
|[**list2**](#list2) | **GET** /user_deck | |
|[**update2**](#update2) | **PUT** /user_deck | |

# **checkDuplicate2**
> UserDeckDuplicateCheckResponse checkDuplicate2(userDeckDuplicateCheckRequest)


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration,
    UserDeckDuplicateCheckRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

let userDeckDuplicateCheckRequest: UserDeckDuplicateCheckRequest; //

const { status, data } = await apiInstance.checkDuplicate2(
    userDeckDuplicateCheckRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDeckDuplicateCheckRequest** | **UserDeckDuplicateCheckRequest**|  | |


### Return type

**UserDeckDuplicateCheckResponse**

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

# **clone1**
> UserDeckIdResponse clone1(userDeckCloneRequest)


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration,
    UserDeckCloneRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

let userDeckCloneRequest: UserDeckCloneRequest; //

const { status, data } = await apiInstance.clone1(
    userDeckCloneRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDeckCloneRequest** | **UserDeckCloneRequest**|  | |


### Return type

**UserDeckIdResponse**

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

# **create2**
> UserDeckIdResponse create2(userDeckCreateRequest)


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration,
    UserDeckCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

let userDeckCreateRequest: UserDeckCreateRequest; //

const { status, data } = await apiInstance.create2(
    userDeckCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDeckCreateRequest** | **UserDeckCreateRequest**|  | |


### Return type

**UserDeckIdResponse**

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

# **delete2**
> delete2()


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.delete2(
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

# **detail2**
> UserDeckDetailResponse detail2()


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.detail2(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**UserDeckDetailResponse**

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

# **list2**
> UserDeckListResponse list2()


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

const { status, data } = await apiInstance.list2();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**UserDeckListResponse**

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

# **update2**
> UserDeckIdResponse update2(userDeckUpdateRequest)


### Example

```typescript
import {
    UserDeckControllerApi,
    Configuration,
    UserDeckUpdateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserDeckControllerApi(configuration);

let userDeckUpdateRequest: UserDeckUpdateRequest; //

const { status, data } = await apiInstance.update2(
    userDeckUpdateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDeckUpdateRequest** | **UserDeckUpdateRequest**|  | |


### Return type

**UserDeckIdResponse**

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

