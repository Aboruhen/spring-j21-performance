import http from 'k6/http';

export const options = {
  stages: [
    {duration: '10s', target: 20},
    // {duration: '10s', target: 500},
    {duration: '10s', target: 3000},
    {duration: '10s', target: 3000},
    {duration: '10s', target: 3000},
    // {duration: '10s', target: 10000},
    {duration: '10s', target: 100},
  ],
};

export default function () {
  http.get('http://localhost:8080/vt-files-performance');
}