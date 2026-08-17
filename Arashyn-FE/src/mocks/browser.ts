import { setupWorker } from "msw/browser";
import { grammar_handler } from "@/mocks/handlers/grammar_handler.ts";
import {auth_handler} from "@/mocks/handlers/auth_handler.ts";

export const worker = setupWorker(
    ...grammar_handler,
    ...auth_handler,
);