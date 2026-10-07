# FolderPublicControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getFolder**](#getfolder) | **GET** /public/folder/{id} | |
|[**getFolderList**](#getfolderlist) | **GET** /public/folder | |

# **getFolder**
> FolderDetailResponse getFolder()


### Example

```typescript
import {
    FolderPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FolderPublicControllerApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.getFolder(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**FolderDetailResponse**

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

# **getFolderList**
> FolderListResponse getFolderList()


### Example

```typescript
import {
    FolderPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FolderPublicControllerApi(configuration);

const { status, data } = await apiInstance.getFolderList();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**FolderListResponse**

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

