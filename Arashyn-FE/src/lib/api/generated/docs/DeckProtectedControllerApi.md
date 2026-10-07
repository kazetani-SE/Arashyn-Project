# DeckProtectedControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**assignDeck**](#assigndeck) | **POST** /protected/deck/assign | |
|[**checkDeckUpdate**](#checkdeckupdate) | **GET** /protected/deck/check-update/{user_deck_id} | |
|[**createDeck**](#createdeck) | **POST** /protected/deck/create | |
|[**deleteDeck**](#deletedeck) | **DELETE** /protected/deck/{deck_id} | |
|[**updateDeck**](#updatedeck) | **POST** /protected/deck/update | |

# **assignDeck**
> DeckIdResponse assignDeck(deckAssignGrammarRequest)


### Example

```typescript
import {
    DeckProtectedControllerApi,
    Configuration,
    DeckAssignGrammarRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckProtectedControllerApi(configuration);

let deckAssignGrammarRequest: DeckAssignGrammarRequest; //

const { status, data } = await apiInstance.assignDeck(
    deckAssignGrammarRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **deckAssignGrammarRequest** | **DeckAssignGrammarRequest**|  | |


### Return type

**DeckIdResponse**

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

# **checkDeckUpdate**
> DeckCheckUpdateResponse checkDeckUpdate()


### Example

```typescript
import {
    DeckProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckProtectedControllerApi(configuration);

let userDeckId: string; // (default to undefined)

const { status, data } = await apiInstance.checkDeckUpdate(
    userDeckId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **userDeckId** | [**string**] |  | defaults to undefined|


### Return type

**DeckCheckUpdateResponse**

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

# **createDeck**
> DeckIdResponse createDeck(deckCreateRequest)


### Example

```typescript
import {
    DeckProtectedControllerApi,
    Configuration,
    DeckCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckProtectedControllerApi(configuration);

let deckCreateRequest: DeckCreateRequest; //

const { status, data } = await apiInstance.createDeck(
    deckCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **deckCreateRequest** | **DeckCreateRequest**|  | |


### Return type

**DeckIdResponse**

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

# **deleteDeck**
> deleteDeck()


### Example

```typescript
import {
    DeckProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckProtectedControllerApi(configuration);

let deckId: string; // (default to undefined)

const { status, data } = await apiInstance.deleteDeck(
    deckId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **deckId** | [**string**] |  | defaults to undefined|


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

# **updateDeck**
> DeckIdResponse updateDeck(deckUpdateRequest)


### Example

```typescript
import {
    DeckProtectedControllerApi,
    Configuration,
    DeckUpdateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new DeckProtectedControllerApi(configuration);

let deckUpdateRequest: DeckUpdateRequest; //

const { status, data } = await apiInstance.updateDeck(
    deckUpdateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **deckUpdateRequest** | **DeckUpdateRequest**|  | |


### Return type

**DeckIdResponse**

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

