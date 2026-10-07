# UserFolderControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**checkDuplicate1**](#checkduplicate1) | **POST** /user_folder/check_duplicate | |
|[**clone**](#clone) | **POST** /user_folder/clone | |
|[**create1**](#create1) | **POST** /user_folder | |
|[**delete1**](#delete1) | **DELETE** /user_folder/{id} | |
|[**detail1**](#detail1) | **GET** /user_folder/{id} | |
|[**list1**](#list1) | **GET** /user_folder | |
|[**root**](#root) | **GET** /user_folder/root | |
|[**update1**](#update1) | **PUT** /user_folder | |

# **checkDuplicate1**
> UserFolderDuplicateCheckResponse checkDuplicate1(userFolderDuplicateCheckRequest)


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration,
    UserFolderDuplicateCheckRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

let userFolderDuplicateCheckRequest: UserFolderDuplicateCheckRequest; //

const { status, data } = await apiInstance.checkDuplicate1(
    userFolderDuplicateCheckRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userFolderDuplicateCheckRequest** | **UserFolderDuplicateCheckRequest**|  | |


### Return type

**UserFolderDuplicateCheckResponse**

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

# **clone**
> UserFolderIdResponse clone(userFolderCloneRequest)


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration,
    UserFolderCloneRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

let userFolderCloneRequest: UserFolderCloneRequest; //

const { status, data } = await apiInstance.clone(
    userFolderCloneRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userFolderCloneRequest** | **UserFolderCloneRequest**|  | |


### Return type

**UserFolderIdResponse**

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

# **create1**
> UserFolderIdResponse create1(userFolderCreateRequest)


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration,
    UserFolderCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

let userFolderCreateRequest: UserFolderCreateRequest; //

const { status, data } = await apiInstance.create1(
    userFolderCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userFolderCreateRequest** | **UserFolderCreateRequest**|  | |


### Return type

**UserFolderIdResponse**

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

# **delete1**
> delete1()


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.delete1(
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

# **detail1**
> UserFolderDetailResponse detail1()


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.detail1(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**UserFolderDetailResponse**

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

# **list1**
> UserFolderListResponse list1()


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

const { status, data } = await apiInstance.list1();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**UserFolderListResponse**

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

# **root**
> UserFolderListResponse root()


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

const { status, data } = await apiInstance.root();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**UserFolderListResponse**

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

# **update1**
> UserFolderIdResponse update1(userFolderUpdateRequest)


### Example

```typescript
import {
    UserFolderControllerApi,
    Configuration,
    UserFolderUpdateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new UserFolderControllerApi(configuration);

let userFolderUpdateRequest: UserFolderUpdateRequest; //

const { status, data } = await apiInstance.update1(
    userFolderUpdateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userFolderUpdateRequest** | **UserFolderUpdateRequest**|  | |


### Return type

**UserFolderIdResponse**

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

