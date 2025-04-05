import React, { useState } from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import axios from 'axios';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { vscDarkPlus } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { config } from '../config';

interface TestCaseRequest {
  dataType: 'ARRAY' | 'STRING' | 'MATRIX' | 'TREE';
  size: number;
  minValue?: number;
  maxValue?: number;
  allowDuplicates?: boolean;
  isSorted?: boolean;
  charset?: string;
  rows?: number;
  columns?: number;
  outputFormat: 'JSON' | 'CSV' | 'PLAIN_TEXT';
  elementType?: 'NUMBER' | 'CHARACTER' | 'STRING';
  stringLength?: number;
  caseType?: 'LOWER' | 'UPPER' | 'MIXED';
  allowSpecialChars?: boolean;
  allowSpaces?: boolean;
  allowNumbers?: boolean;
}

const TestCaseGenerator: React.FC = () => {
  const [request, setRequest] = useState<TestCaseRequest>({
    dataType: 'ARRAY',
    size: 10,
    minValue: -100,
    maxValue: 100,
    allowDuplicates: true,
    isSorted: false,
    outputFormat: 'JSON',
    elementType: 'NUMBER',
    stringLength: 5,
    caseType: 'LOWER',
    allowSpecialChars: false,
    allowSpaces: false,
    allowNumbers: false
  });

  const [result, setResult] = useState<string>('');

  const { data: constraints } = useQuery({
    queryKey: ['constraints'],
    queryFn: async () => {
      const response = await axios.get(config.endpoints.constraints);
      return response.data;
    }
  });

  const generateMutation = useMutation({
    mutationFn: async (data: TestCaseRequest) => {
      const response = await axios.post(config.endpoints.generate, data);
      return response.data;
    },
    onSuccess: (data) => {
      setResult(data);
    }
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    generateMutation.mutate(request);
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(result);
  };

  const handleExport = () => {
    const blob = new Blob([result], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `testcase.${request.outputFormat.toLowerCase()}`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  };

  return (
    <div className="max-w-6xl mx-auto">
      <div className="grid md:grid-cols-2 gap-8">
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg">
          <h2 className="text-2xl font-semibold text-gray-900 dark:text-white mb-6">
            Generate Test Cases
          </h2>
          
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Data Type
              </label>
              <select
                value={request.dataType}
                onChange={(e) => setRequest({ ...request, dataType: e.target.value as TestCaseRequest['dataType'] })}
                className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
              >
                <option value="ARRAY">Array</option>
                <option value="STRING">String</option>
                <option value="MATRIX">Matrix</option>
                <option value="TREE">Tree</option>
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Size/Length
              </label>
              <input
                type="number"
                value={request.size}
                onChange={(e) => setRequest({ ...request, size: parseInt(e.target.value) })}
                className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                min="1"
              />
            </div>

            {request.dataType === 'ARRAY' && (
              <>
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                    Element Type
                  </label>
                  <select
                    value={request.elementType}
                    onChange={(e) => setRequest({ ...request, elementType: e.target.value as TestCaseRequest['elementType'] })}
                    className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                  >
                    <option value="NUMBER">Numbers</option>
                    <option value="CHARACTER">Characters</option>
                    <option value="STRING">Strings</option>
                  </select>
                </div>

                {request.elementType === 'NUMBER' && (
                  <>
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                          Min Value
                        </label>
                        <input
                          type="number"
                          value={request.minValue}
                          onChange={(e) => setRequest({ ...request, minValue: parseInt(e.target.value) })}
                          className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                        />
                      </div>
                      <div>
                        <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                          Max Value
                        </label>
                        <input
                          type="number"
                          value={request.maxValue}
                          onChange={(e) => setRequest({ ...request, maxValue: parseInt(e.target.value) })}
                          className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                        />
                      </div>
                    </div>
                  </>
                )}

                {request.elementType === 'CHARACTER' && (
                  <div>
                    <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      Charset
                    </label>
                    <input
                      type="text"
                      value={request.charset}
                      onChange={(e) => setRequest({ ...request, charset: e.target.value })}
                      className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                      placeholder="abcdefghijklmnopqrstuvwxyz"
                    />
                  </div>
                )}

                {request.elementType === 'STRING' && (
                  <div>
                    <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      String Length
                    </label>
                    <input
                      type="number"
                      value={request.stringLength}
                      onChange={(e) => setRequest({ ...request, stringLength: parseInt(e.target.value) })}
                      className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                      min="1"
                    />
                  </div>
                )}

                <div className="flex space-x-4">
                  <label className="flex items-center">
                    <input
                      type="checkbox"
                      checked={request.allowDuplicates}
                      onChange={(e) => setRequest({ ...request, allowDuplicates: e.target.checked })}
                      className="mr-2"
                    />
                    <span className="text-sm text-gray-700 dark:text-gray-300">Allow Duplicates</span>
                  </label>
                  <label className="flex items-center">
                    <input
                      type="checkbox"
                      checked={request.isSorted}
                      onChange={(e) => setRequest({ ...request, isSorted: e.target.checked })}
                      className="mr-2"
                    />
                    <span className="text-sm text-gray-700 dark:text-gray-300">Sorted</span>
                  </label>
                </div>
              </>
            )}

            {request.dataType === 'STRING' && (
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Charset
                </label>
                <input
                  type="text"
                  value={request.charset}
                  onChange={(e) => setRequest({ ...request, charset: e.target.value })}
                  className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                  placeholder="abcdefghijklmnopqrstuvwxyz"
                />
              </div>
            )}

            {request.dataType === 'MATRIX' && (
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                    Rows
                  </label>
                  <input
                    type="number"
                    value={request.rows}
                    onChange={(e) => setRequest({ ...request, rows: parseInt(e.target.value) })}
                    className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                    min="1"
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                    Columns
                  </label>
                  <input
                    type="number"
                    value={request.columns}
                    onChange={(e) => setRequest({ ...request, columns: parseInt(e.target.value) })}
                    className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                    min="1"
                  />
                </div>
              </div>
            )}

            {(request.dataType === 'STRING' || (request.dataType === 'ARRAY' && request.elementType === 'CHARACTER') || (request.dataType === 'ARRAY' && request.elementType === 'STRING')) && (
              <>
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                    Case Type
                  </label>
                  <select
                    value={request.caseType}
                    onChange={(e) => setRequest({ ...request, caseType: e.target.value as TestCaseRequest['caseType'] })}
                    className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                  >
                    <option value="LOWER">Lowercase</option>
                    <option value="UPPER">Uppercase</option>
                    <option value="MIXED">Mixed Case</option>
                  </select>
                </div>

                <div className="space-y-2">
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                    Character Options
                  </label>
                  <div className="flex flex-col space-y-2">
                    <label className="flex items-center">
                      <input
                        type="checkbox"
                        checked={request.allowNumbers}
                        onChange={(e) => setRequest({ ...request, allowNumbers: e.target.checked })}
                        className="mr-2"
                      />
                      <span className="text-sm text-gray-700 dark:text-gray-300">Include Numbers (0-9)</span>
                    </label>
                    <label className="flex items-center">
                      <input
                        type="checkbox"
                        checked={request.allowSpecialChars}
                        onChange={(e) => setRequest({ ...request, allowSpecialChars: e.target.checked })}
                        className="mr-2"
                      />
                      <span className="text-sm text-gray-700 dark:text-gray-300">Include Special Characters (!@#$%^&* etc.)</span>
                    </label>
                    <label className="flex items-center">
                      <input
                        type="checkbox"
                        checked={request.allowSpaces}
                        onChange={(e) => setRequest({ ...request, allowSpaces: e.target.checked })}
                        className="mr-2"
                      />
                      <span className="text-sm text-gray-700 dark:text-gray-300">Allow Spaces</span>
                    </label>
                  </div>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                    Custom Charset (Optional)
                  </label>
                  <input
                    type="text"
                    value={request.charset || ''}
                    onChange={(e) => setRequest({ ...request, charset: e.target.value })}
                    className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
                    placeholder="Leave empty to use default charset"
                  />
                  <p className="mt-1 text-sm text-gray-500">Custom charset will override other character options</p>
                </div>
              </>
            )}

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Output Format
              </label>
              <select
                value={request.outputFormat}
                onChange={(e) => setRequest({ ...request, outputFormat: e.target.value as TestCaseRequest['outputFormat'] })}
                className="w-full p-2 border rounded-md dark:bg-gray-700 dark:border-gray-600"
              >
                <option value="JSON">JSON</option>
                <option value="CSV">CSV</option>
                <option value="PLAIN_TEXT">Plain Text</option>
              </select>
            </div>

            <button
              type="submit"
              className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 transition-colors"
              disabled={generateMutation.isPending}
            >
              {generateMutation.isPending ? 'Generating...' : 'Generate Test Case'}
            </button>
          </form>
        </div>

        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-semibold text-gray-900 dark:text-white">
              Result
            </h2>
            <div className="flex space-x-2">
              <button
                onClick={handleCopy}
                className="px-3 py-1 text-sm bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-200 dark:hover:bg-gray-600"
              >
                Copy
              </button>
              <button
                onClick={handleExport}
                className="px-3 py-1 text-sm bg-gray-100 dark:bg-gray-700 text-gray-700 dark:text-gray-300 rounded-md hover:bg-gray-200 dark:hover:bg-gray-600"
              >
                Export
              </button>
            </div>
          </div>

          <div className="bg-gray-900 rounded-lg p-4 h-[500px] overflow-auto">
            {result ? (
              <SyntaxHighlighter
                language="json"
                style={vscDarkPlus}
                customStyle={{ margin: 0, background: 'transparent' }}
              >
                {typeof result === 'string' ? result : JSON.stringify(result, null, 2)}
              </SyntaxHighlighter>
            ) : (
              <div className="text-gray-400 text-center mt-4">
                Generate a test case to see the result here
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TestCaseGenerator; 