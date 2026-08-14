/**
 * useSSE Hook
 * Manages Server-Sent Events connections.
 */
import { useState, useEffect } from 'react';

// 📚 CONCEPT: Server-Sent Events (SSE) provide a one-way real-time stream from server to client.
export const useSSE = (url) => {
  const [data, setData] = useState(null);

  useEffect(() => {
    const eventSource = new EventSource(url);
    eventSource.onmessage = (event) => {
      setData(JSON.parse(event.data));
    };
    return () => eventSource.close();
  }, [url]);

  return data;
};
