# GrammarDetailResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **string** |  | [optional] [default to undefined]
**title** | **string** |  | [optional] [default to undefined]
**language** | **string** |  | [optional] [default to undefined]
**isPublic** | **boolean** |  | [optional] [default to undefined]
**ownerId** | **string** |  | [optional] [default to undefined]
**ownerName** | **string** |  | [optional] [default to undefined]
**groups** | [**Array&lt;Group&gt;**](Group.md) |  | [optional] [default to undefined]
**notes** | [**Array&lt;GrammarNoteResponse&gt;**](GrammarNoteResponse.md) |  | [optional] [default to undefined]
**filters** | [**Array&lt;GrammarFilterResponse&gt;**](GrammarFilterResponse.md) |  | [optional] [default to undefined]

## Example

```typescript
import { GrammarDetailResponse } from 'arashyn-api';

const instance: GrammarDetailResponse = {
    id,
    title,
    language,
    isPublic,
    ownerId,
    ownerName,
    groups,
    notes,
    filters,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
