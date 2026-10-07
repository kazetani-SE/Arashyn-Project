# DeckPublicControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getDeck**](#getdeck) | **GET** /public/deck/{deck_id} | |
|[**getDeckList**](#getdecklist) | **GET** /public/deck | |

# **getDeck**
> DeckDetailResponse getDeck()


### Example

```typescript
import {
    DeckPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckPublicControllerApi(configuration);

let deckId: string; // (default to undefined)

const { status, data } = await apiInstance.getDeck(
    deckId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **deckId** | [**string**] |  | defaults to undefined|


### Return type

**DeckDetailResponse**

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

# **getDeckList**
> DeckListResponse getDeckList()


### Example

```typescript
import {
    DeckPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckPublicControllerApi(configuration);

const { status, data } = await apiInstance.getDeckList();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**DeckListResponse**

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

