# FolderProtectedControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**checkUpdate**](#checkupdate) | **GET** /protected/folder/check-update/{user_folder_id} | |
|[**create3**](#create3) | **POST** /protected/folder | |
|[**delete3**](#delete3) | **DELETE** /protected/folder/{id} | |
|[**update3**](#update3) | **PUT** /protected/folder | |

# **checkUpdate**
> FolderCheckUpdateResponse checkUpdate()


### Example

```typescript
import {
    FolderProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FolderProtectedControllerApi(configuration);

let userFolderId: string; // (default to undefined)

const { status, data } = await apiInstance.checkUpdate(
    userFolderId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userFolderId** | [**string**] |  | defaults to undefined|


### Return type

**FolderCheckUpdateResponse**

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

# **create3**
> FolderIdResponse create3(folderCreateRequest)


### Example

```typescript
import {
    FolderProtectedControllerApi,
    Configuration,
    FolderCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FolderProtectedControllerApi(configuration);

let folderCreateRequest: FolderCreateRequest; //

const { status, data } = await apiInstance.create3(
    folderCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **folderCreateRequest** | **FolderCreateRequest**|  | |


### Return type

**FolderIdResponse**

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

# **delete3**
> delete3()


### Example

```typescript
import {
    FolderProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FolderProtectedControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.delete3(
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

# **update3**
> FolderIdResponse update3(folderUpdateRequest)


### Example

```typescript
import {
    FolderProtectedControllerApi,
    Configuration,
    FolderUpdateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FolderProtectedControllerApi(configuration);

let folderUpdateRequest: FolderUpdateRequest; //

const { status, data } = await apiInstance.update3(
    folderUpdateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **folderUpdateRequest** | **FolderUpdateRequest**|  | |


### Return type

**FolderIdResponse**

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

